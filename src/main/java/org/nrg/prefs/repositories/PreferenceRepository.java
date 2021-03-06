/*
 * ddict.repositories.ResourceRepository
 *
 * Copyright (c) 2014, Washington University School of Medicine
 * All Rights Reserved
 *
 * XNAT is an open-source project of the Neuroinformatics Research Group.
 * Released under the Simplified BSD.
 *
 * Last modified 5/1/14 11:06 AM
 */

package org.nrg.prefs.repositories;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.type.StandardBasicTypes;
import org.nrg.framework.constants.Scope;
import org.nrg.framework.orm.hibernate.AbstractHibernateDAO;
import org.nrg.framework.scope.EntityId;
import org.nrg.prefs.entities.Preference;
import org.nrg.prefs.entities.Tool;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages preferences within the preferences service framework.
 */
@Repository
public class PreferenceRepository extends AbstractHibernateDAO<Preference> {
    public Preference findByToolIdNameAndEntity(final String toolId, final String preferenceName, final Scope scope, final String entityId) {
        @SuppressWarnings("all")
        final Object results = getSession().createSQLQuery("select pref.id as id from xhbm_preference as pref, xhbm_tool as tool where tool.tool_id = :toolId and tool.id = pref.tool and pref.name = :preferenceName and pref.scope = :scope and pref.entity_id = :entityId")
                                           .addScalar("id", StandardBasicTypes.LONG)
                                           .setString("toolId", toolId)
                                           .setString("preferenceName", preferenceName)
                                           .setInteger("scope", scope == null ? EntityId.Default.getScope().ordinal() : scope.ordinal())
                                           .setString("entityId", resolveEntityId(entityId))
                                           .uniqueResult();
        if (results == null) {
            return null;
        }
        if (results instanceof Long) {
            return retrieve((Long) results);
        }
        return null;
    }

    public Preference findByToolNameAndEntity(final Tool tool, final String preferenceName, final Scope scope, final String entityId) {
        // TODO: This doesn't work. It seems like a bug. With two tools, this will find existing preferences for the first tool when trying to create preferences for the second, even though the tool is set to the second instance.
        // final Preference example = new Preference();
        // example.setTool(tool);
        // example.setName(preferenceName);
        // final List<Preference> found = findByExample(example, EXCLUDE_PROPERTY);
        // if (found.size() == 0) {
        //     return null;
        // }
        // return found.get(0);
        return findByToolIdNameAndEntity(tool.getToolId(), preferenceName, scope, entityId);
    }

    public List<Preference> findByToolIdAndEntity(final String toolId, final Scope scope, final String entityId) {
        @SuppressWarnings("all")
        final List results = getSession().createSQLQuery("select * from xhbm_preference as pref, xhbm_tool as tool where tool.tool_id = :toolId and tool.id = pref.tool and pref.scope = :scope and pref.entity_id = :entityId")
                .addScalar("id", StandardBasicTypes.LONG)
                .setString("toolId", toolId)
                .setInteger("scope", scope == null ? EntityId.Default.getScope().ordinal() : scope.ordinal())
                .setString("entityId", resolveEntityId(entityId))
                .list();
        if (results == null || results.size() == 0) {
            return new ArrayList<>();
        }
        final List<Preference> preferences = new ArrayList<>();
        for (final Object result : results) {
            if (result instanceof Long) {
                preferences.add(retrieve((Long) result));
            }
        }
        return preferences;
    }

    private static String resolveEntityId(final String entityId) {
        return StringUtils.isBlank(entityId) ? EntityId.Default.getEntityId() : entityId;
    }

    // private static final String[] EXCLUDE_PROPERTY = new String[]{"id", "enabled", "created", "timestamp", "disabled", "value"};
}
