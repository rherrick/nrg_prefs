package org.nrg.prefs.tools.relaxed;

import org.nrg.prefs.exceptions.InvalidPreferenceName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RelaxedPrefsTool {
    public String getRelaxedPrefA() {
        return _preferences.getRelaxedPrefA();
    }

    public void setRelaxedPrefA(final String relaxedPrefA) throws InvalidPreferenceName {
        _preferences.setRelaxedPrefA(relaxedPrefA);
    }

    public String getRelaxedPrefB() {
        return _preferences.getRelaxedPrefB();
    }

    public void setRelaxedPrefB(final String relaxedPrefB) throws InvalidPreferenceName {
        _preferences.setRelaxedPrefB(relaxedPrefB);
    }

    public String getRelaxedPrefC() {
        return _preferences.getRelaxedPrefC();
    }

    public void setRelaxedPrefC(final String relaxedPrefC) throws InvalidPreferenceName {
        _preferences.setRelaxedPrefC(relaxedPrefC);
    }

    public String getRelaxedWhatever(final String preference) {
        return _preferences.getValue(preference);
    }

    public void setRelaxedWhatever(final String preference, final String value) throws InvalidPreferenceName {
        _preferences.set(value, preference);
    }

    @Autowired
    private RelaxedPrefsToolPreferenceBean _preferences;
}
