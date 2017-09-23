package com.wildbeeslabs.api.rest.common.repository;

import com.wildbeeslabs.api.rest.common.model.IBaseEntity;
import java.util.Date;
import java.util.List;

//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

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
    //@Query("select e from #{#entityName} e where e.createdAt <= ?1")
    List<? extends T> findByCreatedAtLessThanEqual(final Date date);

    /**
     * Get list of entities created after (excluding) particular date
     *
     * @param date - request date
     * @return list of entities
     */
    //@Query("select e from #{#entityName} e where e.createdAt > ?1")
    List<? extends T> findByCreatedAtGreaterThan(final Date date);

    /**
     * Get list of entities created by date period
     *
     * @param dateFrom - start date of period (excluding)
     * @param dateTo - end date of period (including)
     * @return list of entities
     */
    //@Query("select e from #{#entityName} e where e.createdAt > ?1 and e.createdAt <= ?2")
    List<? extends T> findByCreatedAtBetween(final Date dateFrom, final Date dateTo);

    /**
     * Get list of entities created by user
     *
     * @param createdBy - user name who created entities
     * @return list of entities
     */
    //@Query("select e from #{#entityName} e where e.createdBy = ?1")
    List<? extends T> findByCreatedByIgnoreCase(final String createdBy);

    /**
     * Get list of entities modified by user
     *
     * @param modifiedBy - user name who modified entities
     * @return list of entities
     */
    //@Query("select e from #{#entityName} e where e.modifiedBy = ?1")
    List<? extends T> findByModifiedByIgnoreCase(final String modifiedBy);
}
