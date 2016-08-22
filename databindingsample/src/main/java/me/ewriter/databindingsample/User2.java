package me.ewriter.databindingsample;

import android.databinding.ObservableArrayMap;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableMap;

/**
 * Created by Zubin on 2016/8/22.
 */
public class User2 {

    public final ObservableField<String> userName = new ObservableField<>();

    public final ObservableField<String> password = new ObservableField<>();

    public final ObservableInt age = new ObservableInt();

    ObservableArrayMap<String, Object> user = new ObservableArrayMap<>();


}
