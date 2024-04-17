import React from "react";
import Pagination from "./Pagination";
import sample from "../activitieswidget/sampleconfig";

function PaginationWrapper() {
    const contract = {
        pageCount: 10, // Part of aem-react App
        marginPagesDisplayed: 1,
        onPageChange: props => console.log(props), // CallBack
        isMobile: true,
    };

    return ( 
        <Pagination
        pageCount= {10} // Part of aem-react App
        marginPagesDisplayed= {1}
        onPageChange= {props => console.log(props)} // CallBack
        isMobile={true}
        /> 
    );
}

export default PaginationWrapper;