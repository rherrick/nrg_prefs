/*
 * org.nrg.prefs.services.NrgPrefsService
 *
 * Copyright (c) 2016, Washington University School of Medicine
 * All Rights Reserved
 *
 * XNAT is an open-source project of the Neuroinformatics Research Group.
 * Released under the Simplified BSD.
 */
package org.nrg.prefs.services;

import org.nrg.framework.constants.Scope;
import org.nrg.framework.services.NrgService;
import org.nrg.prefs.annotations.NrgPreferenceBean;
import org.nrg.prefs.beans.AbstractPreferenceBean;
import org.nrg.prefs.beans.PreferenceBean;
import org.nrg.prefs.entities.Preference;
import org.nrg.prefs.entities.Tool;
import org.nrg.prefs.exceptions.InvalidPreferenceName;
import org.nrg.prefs.exceptions.UnknownToolId;

import java.util.Properties;
import java.util.Set;

/**
 * The preferences service interface is the primary means of working with preferences
 * within the XNAT service context.
 */
public interface NrgPreferenceService extends NrgService {
    /**
     * Creates a {@link Tool tool} with the properties and values specified on the preferences bean and its related
     * {@link NrgPreferenceBean NrgPreferenceBean annotation}.
     *
     * @param bean The bean object.
     * @return The object representing the persisted tool definition.
     */
    Tool createTool(final PreferenceBean bean) throws InvalidPreferenceName;

    /**
     * Creates the submitted {@link Tool tool} with the properties and values specified on the tool preferences bean.
     * This tool works without a dedicated {@link AbstractPreferenceBean preferences bean object}.
     *
     * @param tool The tool to create.
     * @return The newly created tool object.
     */
    Tool createTool(final Tool tool) throws InvalidPreferenceName;

    /**
     * Checks whether the preference exists for the indicated tool. This checks at the {@link Scope#Site site scope}. If
     * you need to specify the preference for a particular entity, use the {@link #getPreference(String, String, Scope,
     * String)} form of this method instead.
     *
     * @param toolId     The unique tool ID.
     * @param preference The preference name.
     *
     * @return Returns true if the preference exists for the tool, false otherwise.
     */
    boolean hasPreference(final String toolId, final String preference);

    /**
     * Checks whether the preference exists for the indicated tool and entity.
     *
     * @param toolId     The unique tool ID.
     * @param preference The preference name.
     * @param scope      The scope of the object identified by the entityId parameter.
     * @param entityId   The ID of the particular object associated with the preference.
     *
     * @return Returns true if the preference exists for the tool, false otherwise.
     */
    boolean hasPreference(final String toolId, final String preference, final Scope scope, final String entityId);

    /**
     * Gets the preference for the indicated tool. This retrieves the preference for the {@link Scope#Site site scope}.
     * If you need to specify the preference for a particular entity, use the {@link #getPreference(String, String,
     * Scope, String)} form of this method instead.
     *
     * @param toolId     The unique tool ID.
     * @param preference The preference name.
     * @return The {@link Preference preference} for the indicated tool and preference name.
     */
    Preference getPreference(final String toolId, final String preference) throws UnknownToolId;

    /**
     * Gets the preference for the indicated tool and entity.
     *
     * @param toolId     The unique tool ID.
     * @param preference The preference name.
     * @param scope      The scope of the object identified by the entityId parameter.
     * @param entityId   The ID of the particular object associated with the preference.
     * @return The {@link Preference preference} for the corresponding tool and entity.
     * @throws UnknownToolId When the system can't find a tool with the indicated ID.
     */
    Preference getPreference(final String toolId, final String preference, final Scope scope, final String entityId) throws UnknownToolId;

    /**
     * Get the preference value for the indicated tool and preference. If the preference value is a string, then this
     * just returns that. If the value is a non-string, the string representation of the value is returned. This
     * retrieves the preference value for the {@link Scope#Site site scope}. If you need to specify the preference for a
     * particular entity, use the {@link #getPreferenceValue(String, String, Scope, String)} form of this method
     * instead.
     *
     * @param toolId     The unique tool ID.
     * @param preference The preference name.
     * @return The string value for the indicated preference.
     */
    String getPreferenceValue(final String toolId, final String preference) throws UnknownToolId;

    /**
     * Get the preference value for the indicated tool, preference, and entity. If the preference value is a string,
     * then this just returns that. If the value is a non-string, the string representation of the value is returned.
     *
     * @param toolId     The unique tool ID.
     * @param preference The preference name.
     * @param scope      The scope of the object identified by the entityId parameter.
     * @param entityId   The ID of the particular object associated with the preference.
     * @return The string value for the indicated preference.
     * @throws UnknownToolId When the system can't find a tool with the indicated ID.
     */
    String getPreferenceValue(final String toolId, final String preference, final Scope scope, final String entityId) throws UnknownToolId;

    /**
     * Sets the preference value for the indicated tool and preference. The preference value must be coerced to a
     * string. This sets the preference value for the {@link Scope#Site site scope}. If you need to set the preference
     * value for a particular entity, use the {@link #setPreferenceValue(String, String, Scope, String, String)} form of
     * this method instead.
     *
     * @param toolId     The unique tool ID.
     * @param preference The preference name.
     * @param value      The value to set for the preference.
     * @throws UnknownToolId         When the system can't find a tool with the indicated ID.
     * @throws InvalidPreferenceName When the system can't find a preference with the indicated name associated with the tool with the indicated ID.
     */
    void setPreferenceValue(final String toolId, final String preference, final String value) throws UnknownToolId, InvalidPreferenceName;

    /**
     * Sets the preference value for the indicated tool, preference, and entity. The preference value must be coerced to
     * a string.
     *
     * @param toolId     The unique tool ID.
     * @param preference The preference name.
     * @param scope      The scope of the object identified by the entityId parameter.
     * @param entityId   The ID of the particular object associated with the preference.
     * @param value      The value to set for the preference.
     * @throws UnknownToolId         When the system can't find a tool with the indicated ID.
     * @throws InvalidPreferenceName When the system can't find a preference with the indicated name associated with the tool with the indicated ID.
     */
    void setPreferenceValue(final String toolId, final String preference, final Scope scope, final String entityId, final String value) throws UnknownToolId, InvalidPreferenceName;

    /**
     * Deletes the indicated preference for the tool and specified entity. This deletes the preference value for the
     * {@link Scope#Site site scope}. If you need to delete the preference value for a particular entity, use the {@link
     * #deletePreference(String, String, Scope, String)} form of this method instead.
     *
     * @param toolId     The unique tool ID.
     * @param preference The preference name.
     * @throws InvalidPreferenceName When the system can't find a preference with the indicated name associated with the tool with the indicated ID.
     */
    void deletePreference(final String toolId, final String preference) throws InvalidPreferenceName;

    /**
     * Deletes the indicated preference for the tool and specified entity.
     *
     * @param toolId     The unique tool ID.
     * @param preference The preference name.
     * @param scope      The scope of the object identified by the entityId parameter.
     * @param entityId   The ID of the particular object associated with the preference.
     * @throws InvalidPreferenceName When the system can't find a preference with the indicated name associated with the tool with the indicated ID.
     */
    void deletePreference(final String toolId, final String preference, final Scope scope, final String entityId) throws InvalidPreferenceName;

    /**
     * Gets a set of all of the tool names with preferences stored in the service.
     *
     * @return A set of all available tool names.
     */
    Set<String> getToolIds();

    /**
     * Gets a set of all of the tools with preferences stored in the service.
     *
     * @return A set of all available tools.
     */
    Set<Tool> getTools();

    /**
     * Gets a list of all of the property names associated with the indicated {@link Tool tool}.
     *
     * @param toolId The unique tool ID.
     * @return A list of all of the property names for the indicated tool.
     */
    @SuppressWarnings("unused")
    Set<String> getToolPropertyNames(final String toolId);

    /**
     * Gets all of the properties associated with the indicated {@link Tool tool} in the form of a standard Java
     * properties object.
     *
     * @param toolId The unique tool ID.
     * @return All of the properties for the indicated tool.
     */
    Properties getToolProperties(final String toolId);
}
