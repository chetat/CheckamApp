package com.yekuwilfred.checkam;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;

/**
 * Created by YEKUWILFRED on 4/22/2017.
 */

public class Aplace{
    private Place place;
   public Aplace(PlaceBuffer places){
      this.place = places.get(0);
       place.freeze();
   }

    public String getPlaceName() {
        return place.getName().toString();
    }
}
