import GlobalCarousel from "./Carousel";
import axios from "axios";
import React, {useEffect, useState} from "react";
import { getEnvironment } from "../../helpers/getEnvironment";
import CONSTANTS from "../../constants";

function CarouselWrapper({json_url, runmodes}) {

    const [authorConfig, setauthorConfig] = useState(null);

    useEffect(()=>{
      axios(json_url).then((json) => {

        const slides = json.data.carouselSlides

        slides.forEach(s=> {
          if(!s.bgColor) s.bgColor = '#fff'
          if(!s.headerColor) s.headerColor = '#131582'

          const a = s.actions

          a.forEach(action => action.text = action.linkText)
        })

        setauthorConfig(json.data);
      });
    }, [])

    
    if(!authorConfig) {
        return (<div/>)
    }

    /* 
    This code will hide the carousel on iOS, in PROD only. 
    Leaving it here in case we need it again. 
    if(
        /iPad|iPhone|iPod/.test(navigator.userAgent) 
        && getEnvironment(runmodes) == CONSTANTS.ENVIRONMENT_PROD
      ) {
        return <div></div>
    } */

    return (<GlobalCarousel contract={authorConfig}/>);

}

export default CarouselWrapper;