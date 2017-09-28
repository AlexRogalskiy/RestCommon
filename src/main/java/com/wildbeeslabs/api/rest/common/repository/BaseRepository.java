package com.wildbeeslabs.api.rest.common.repository;

import com.wildbeeslabs.api.rest.common.model.IBaseEntity;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.scheduling.annotation.Async;

/**
 *
 * Base REST Application storage repository
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
@NoRepositoryBean
public interface BaseRepository<T extends IBaseEntity> {

    /**
     * Get list of entities created before (including) particular date
     *
     * @param date - request date
     * @return list of entities
     */
    //@Query("SELECT e FROM #{#entityName} e WHERE e.createdAt <= ?1")
    @Async
    CompletableFuture<List<? extends T>> findByCreatedAtLessThanEqual(final Date date);

    /**
     * Get list of entities created after (excluding) particular date
     *
     * @param date - request date
     * @return list of entities
     */
    //@Query("SELECT e FROM #{#entityName} e WHERE e.createdAt > ?1")
    @Async
    CompletableFuture<List<? extends T>> findByCreatedAtGreaterThan(final Date date);

    /**
     * Get list of entities created by date period
     *
     * @param dateFrom - start date of period (excluding)
     * @param dateTo - end date of period (including)
     * @return list of entities
     */
    //@Query("SELECT e FROM #{#entityName} e WHERE e.createdAt > ?1 AND e.createdAt <= ?2")
    @Async
    CompletableFuture<List<? extends T>> findByCreatedAtBetween(final Date dateFrom, final Date dateTo);

    /**
     * Get list of entities created by user
     *
     * @param createdBy - user name who created entities
     * @return list of entities
     */
    //@Query("SELECT e FROM #{#entityName} e WHERE e.createdBy = ?1")
    @Async
    CompletableFuture<List<? extends T>> findByCreatedByIgnoreCase(final String createdBy);

    /**
     * Get list of entities modified by user
     *
     * @param modifiedBy - user name who modified entities
     * @return list of entities
     */
    //@Query("SELECT e FROM #{#entityName} e WHERE e.modifiedBy = ?1")
    @Async
    CompletableFuture<List<? extends T>> findByModifiedByIgnoreCase(final String modifiedBy);
}
