package com.example.internsearchapp.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class LocationIQPlace {
    @SerializedName("place_id")
    private String placeId;

    @SerializedName("display_name")
    private String displayName;

    private String lat;
    private String lon;

    public String getPlaceId() { return placeId == null ? (lat + "," + lon) : placeId; }
    public String getDisplayName() { return displayName == null ? "" : displayName; }
    public String getLat() { return lat == null ? "" : lat; }
    public String getLon() { return lon == null ? "" : lon; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationIQPlace that = (LocationIQPlace) o;
        return Objects.equals(getPlaceId(), that.getPlaceId()) &&
               Objects.equals(getDisplayName(), that.getDisplayName()) &&
               Objects.equals(getLat(), that.getLat()) &&
               Objects.equals(getLon(), that.getLon());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlaceId(), getDisplayName(), getLat(), getLon());
    }
}
