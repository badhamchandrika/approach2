import React, {useState} from "react";

function Suggestion({suggestion, index, setter}) {
    const {Code, Name, CountryCode} = suggestion

    const handleClick=(e)=>{
        setter(prev=>{
            const locations = [...prev]
            locations[index] = Code
            return locations
        })
    }

    return (
        <div className="location-input__suggestion widget-sugggestions" onClick={handleClick}>
            <span className="suggestion__text widget-sugggestions">{`${Name}, `}</span>
            <span className="suggestion__text widget-sugggestions">{`${CountryCode} `}</span>
            <span className="suggestion__text widget-sugggestions">{`(${Code})`}</span>
        </div>
    );
}

export default Suggestion;