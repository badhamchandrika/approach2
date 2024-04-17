import React, { useEffect, useState, useRef } from 'react';
import ReactPaginate from 'react-paginate';
import Container from '@loyaltyone-am/container';
import cn from 'classnames';
import '@loyaltyone-am/container/dist/index.css';

  const Pagination = ({     
    pageCount,
    marginPagesDisplayed = 1,
    isMobile,
    onPageChange = () => null,
    style = {},
    previousAriaLabel = 'Previous page',
    nextAriaLabel = 'Next page', 
    currentPage,
    containerTopRef,
    pagination_background_color,
  }) => {


  const classes = "AMPagination__rebrand";

  const totalElements = isMobile ? 5 : 7;
  const pageRangeDisplayed = isMobile ? 3 : 5;
  const [pageRange, setPageRange] = useState(pageRangeDisplayed - 1);
  const container = useRef()

  const setInitialValues = () => {
    if (pageCount <= totalElements) {
      setPageRange(pageCount);
    }
  };

  const removeDisabledButtonsFromTabIndex = ()=>{
    const prevArrow = container.current.getElementsByClassName('previous')[0]
    const nextArrow = container.current.getElementsByClassName('next')[0]

    if(currentPage === 1){

      prevArrow.style.display = 'none';
      nextArrow.style.display = 'block';
      

      return
    }
    if(currentPage === pageCount){
      nextArrow.style.display = 'none';
      prevArrow.style.display = 'block';

      return
    }
    nextArrow.style.display = 'block';
    prevArrow.style.display = 'block';
  }

  useEffect(() => {
    setInitialValues();
  }, []);


  useEffect(removeDisabledButtonsFromTabIndex, [pageCount, currentPage])

  const calculatePageRange = currentPageSelected => {
    if (pageCount > totalElements) {
      if (pageCount - currentPageSelected <= 2) {
        setPageRange(pageRangeDisplayed);
      } else if (
        isMobile &&
        currentPageSelected > pageRangeDisplayed - 2 &&
        pageCount - currentPageSelected > pageRangeDisplayed - 2
      ) {
        setPageRange(pageRangeDisplayed - 2);
      } else if (
        !isMobile &&
        currentPageSelected >= pageRangeDisplayed - 2 &&
        pageCount - currentPageSelected > pageRangeDisplayed - 2
      ) {
        setPageRange(pageRangeDisplayed - 2);
      } else {
        setPageRange(pageRangeDisplayed - 1);
      }
    }
  };

  const paginationPageChange = props => {
    const topOfDealsList = containerTopRef
      .current
      .getBoundingClientRect()
      .top

    window.scrollBy({top: topOfDealsList, behavior: 'instant'})

    calculatePageRange(props.selected);
    onPageChange(props.selected + 1);
  };

  return (
    <div className={`AMPagination ${classes} card-background-color-${pagination_background_color}`} ref={container}>
      <Container>
        <div
          style={{
            ...style,
          }}
          className={`AMPagination ${classes}`}
        >
          <ReactPaginate
            previousLabel={<span class="sr-only">{previousAriaLabel}</span>}
            nextLabel={<span class="sr-only">{nextAriaLabel}</span>}
            nextLinkClassName="AMPagination__arrow am-icon am-icon-functional-arrow-right"
            previousLinkClassName="AMPagination__arrow am-icon am-icon-functional-arrow-left"
            pageClassName={cn("AMPagination__li-item", `${classes}__li-item`)}
            disabledClassName={cn("AMPagination__arrow--disabled", `${classes}__arrow--disabled`)}
            activeClassName={cn("AMPagination__active", `${classes}__active`)}
            breakLabel="..."
            breakClassName="break-me"
            pageCount={pageCount}
            marginPagesDisplayed={marginPagesDisplayed}
            pageRangeDisplayed={pageRange}
            onPageChange={props => paginationPageChange(props)}
            subContainerClassName="pages pagination"
            {...(typeof currentPage !== 'undefined' && !Number.isNaN(currentPage)
              ? {
                forcePage: Number(currentPage) - 1,
              }
              : {})}
          />
        </div>
      </Container>
    </div >
  );
};
//
export default Pagination;

