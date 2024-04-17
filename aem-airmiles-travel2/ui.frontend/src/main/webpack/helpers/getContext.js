import { ActivitiesContext } from "../components/activitieswidget/ActivitiesContext";
import { BundlingContext } from "../components/bundlingwidget/BundlingContext";
import { CarRentalContext } from "../components/carrentalwidget/CarRentalContext";
import { FlightsContext } from "../components/flightswidget/FlightsContext";
import { PackagesContext } from "../components/packageswidget/PackagesContext";
import { StaysContext } from "../components/stayswidget/StaysContext";
import CONSTANTS from "../constants";

export const getContext = (parentWidget)=>{
  switch (parentWidget) {
    case CONSTANTS.WIDGET.ACTIVITIES:
      return ActivitiesContext;  
    case CONSTANTS.WIDGET.PACKAGES:
      return PackagesContext;  
    case CONSTANTS.WIDGET.RENTALS:
      return CarRentalContext;  
    case CONSTANTS.WIDGET.STAYS:
      return StaysContext;  
    case CONSTANTS.WIDGET.BUNDLING:
      return BundlingContext;  
    default:
      return FlightsContext;
  }
}; 
