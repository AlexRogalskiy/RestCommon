/*
 * The MIT License
 *
 * Copyright 2017 Pivotal Software, Inc..
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.wildbeeslabs.api.rest.common.utils;

import com.wildbeeslabs.api.rest.common.utils.security.UserCredentials;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.access.intercept.RunAsUserToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Base64;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
//import org.springframework.data.authentication.UserCredentials;

public final class SecurityUtils {

    public static final String ANONYMOUS_USER = "anonymousUser";
    public static final String SEC_CLIENT = "sec_client";

    /**
     * Default private constructor
     */
    private SecurityUtils() {
    }

    public static User authenticate(final String key, final String uuid) {
        final UserCredentials principal = new UserCredentials(randomAlphabetic(6), randomAlphabetic(6), true, new ArrayList<>(), uuid);
        SecurityContextHolder.getContext().setAuthentication(new RunAsUserToken(key, principal, null, new ArrayList<>(), null));
        return principal;
    }

    //
    /**
     * Gets the current user details.
     *
     * @return the current user details or null if can't be retrieved.
     */
    public static UserDetails getCurrentUserDetails() {
        final Authentication authentication = getCurrentAuthentication();
        if (Objects.isNull(authentication)) {
            return null;
        }

        final Object principal = authentication.getPrincipal();
        if (Objects.isNull(principal)) {
            return null;
        }
        if (principal instanceof UserDetails) {
            return (UserDetails) principal;
        }
        return null;
    }

    public static Authentication getCurrentAuthentication() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        if (Objects.isNull(securityContext)) {
            return null;
        }
        return securityContext.getAuthentication();
    }

    public static UserCredentials getCurrentPrincipal() {
        final Authentication currentAuthentication = getCurrentAuthentication();
        if (Objects.isNull(currentAuthentication)) {
            return null;
        }
        final Object principal = currentAuthentication.getPrincipal();
        if (Objects.isNull(principal)) {
            return null;
        }
        return (UserCredentials) principal;
    }

    public static String getNameOfCurrentPrincipal() {
        final Authentication authentication = getCurrentAuthentication();
        if (Objects.isNull(authentication)) {
            return null;
        }
        return authentication.getName();
    }

    public static String getUuidOfCurrentPrincipal() {
        final UserCredentials currentPrincipal = getCurrentPrincipal();
        if (Objects.isNull(currentPrincipal)) {
            return null;
        }
        return currentPrincipal.getUuid();
    }

    // is?
    /**
     * Check if current user is authenticated.
     *
     * @return true if user is authenticated.
     */
    public static boolean isAuthenticated() {
        return Objects.nonNull(SecurityUtils.getCurrentUserDetails());
    }

    /**
     * Check if current user is anonymous guest.
     *
     * @return true if user is anonymous guest.
     */
    public static boolean isAnonymous() {
        return Objects.nonNull(SecurityUtils.getCurrentUserDetails());
    }

    // has?
    /**
     * Check if current user has specified role.
     *
     * @param privilege the role to check if user has.
     * @return true if user has specified role, otherwise false.
     */
    public static boolean hasPrivilege(final String privilege) {
        final UserDetails userDetails = SecurityUtils.getCurrentUserDetails();
        if (Objects.nonNull(userDetails)) {
            if (userDetails.getAuthorities().stream().anyMatch((each) -> (each.getAuthority().equals(privilege)))) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check if current user has any role of specified.
     *
     * @param privileges the array of roles.
     * @return true if has any role, otherwise false.
     */
    public static boolean hasAnyPrivilege(final String... privileges) {
        final UserDetails userDetails = SecurityUtils.getCurrentUserDetails();
        if (Objects.nonNull(userDetails)) {
            final Set<String> rolesSet = new HashSet();
            rolesSet.addAll(Arrays.asList(privileges));
            if (userDetails.getAuthorities().stream().anyMatch((each) -> (rolesSet.contains(each.getAuthority())))) {
                return true;
            }
        }
        return false;
    }

    // auth
    /**
     * Calculates an authorization key for user.
     *
     * @param userDetails the user details.
     * @return the calculated authorization key.
     */
    public static String encodeAuthorizationKey(final UserDetails userDetails) {
        return encodeAuthorizationKey(userDetails.getUsername(), userDetails.getPassword());
    }

    public static String encodeAuthorizationKey(final String username, final String password) {
        final String authorizationString = username + ":" + password;
        return new String(Base64.encode(authorizationString.getBytes(Charset.forName("UTF-8"))));
    }

    public static Pair<String, String> decodeAuthorizationKey(final String basicAuthValue) {
        if (Objects.isNull(basicAuthValue)) {
            return null;
        }
        final byte[] decodeBytes = Base64.decode(basicAuthValue.substring(basicAuthValue.indexOf(' ') + 1).getBytes(Charset.forName("UTF-8")));
        String decoded = null;
        try {
            decoded = new String(decodeBytes, "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            return null;
        }
        final int indexOfDelimiter = decoded.indexOf(':');
        final String username = decoded.substring(0, indexOfDelimiter);
        final String password = decoded.substring(indexOfDelimiter + 1);
        return new ImmutablePair<>(username, password);
    }
}
