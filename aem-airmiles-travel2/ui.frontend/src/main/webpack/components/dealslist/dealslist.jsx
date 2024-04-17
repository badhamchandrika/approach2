import React, {useEffect, useMemo, useRef, useState} from 'react';
import { useMediaQuery } from "react-responsive";
import classNames from 'classnames';
import axios from 'axios';

import Filters from '../ui/Filters/Filters';
import Pagination from '../Pagination/Pagination';
import Card from '../ui/Card';
import Sort from '../ui/Sort/Sort';
import CONSTANTS from '../../constants';
import { doesDealMatchFilters } from './doesDealMatchFilters';
import Pills from '../ui/Pills';
import InLineDropdown from '../React/InlineDropdown/InlineDropdown';
import PackagesWidget from '../packageswidget/packageswidget';

const MAX_CARDS_PER_PRODUCT_PAGE = 4;

function DealsList({
		json_cards_url,
		json_filters_url,
		sort_label,
		sort_recent,
		sort_expire,
		filter_modal_title,
		filter_title,
		sort_modal_button,
		filter_button_mobile,
		filter_clear,
		filter_show_all,
		results_label,
		title,
		product_page,
		is_author,
		hasSmallWidget = true,
		showPackagesWidget = false,
		pagination_background_color,
	}){

	const [dealsList, setDealsList] = useState([])
	const [filterItems, setFilterItems] = useState([]);
	const [filterGroups, setFilterGroups] = useState([]);
	const [sortBy, setSortBy] = useState(CONSTANTS.DEALS.DATE_ADDED);
	const [dealsThatMatchFilters, setDealsThatMatchFilters] = useState([])
	const [currentPage, setCurrentPage] = useState(1)
	const [variant, setVariant] = useState("")
	const containerTopRef = useRef()
	const dealListRef = useRef();

	const isDesktop = useMediaQuery({ minWidth: CONSTANTS.BREAKPOINTS.XL, });

	useEffect(()=>{
		 axios
			.get(json_cards_url)
			.then(({data})=> {
      const { deals, variant } = data;
      deals.forEach(deal=>{
        deal.variant = variant;

        if (deal.startDate) {
          deal.dateAdded = new Date(deal.startDate);
        }
        if (deal.date) {
          deal.expiryDateObj = new Date(deal.date);
          deal.date = new Date(deal.date)
            .toISOString()
							.split('T')[0] // formats to YYYY/MM/DD
							.replace(/-/g, "/")
        }
      })
      const today = new Date();
      const validDeals = deals.filter((deal) =>
        deal.variant !== CONSTANTS.CARD_VARIANTS.DEAL
          ? true
          : (deal.dateAdded ? today >= deal.dateAdded : true) &&
            (deal.expiryDateObj ? today <= deal.expiryDateObj : true)
      );
	  validDeals.sort((a,b)=>b[sortBy] - a[sortBy])
	  hideAemCardTitle(validDeals);
	  setVariant(variant);
      setDealsList(validDeals);

	  axios
	  .get(json_filters_url)
	  .then(({data}) => {
		  const groups = data.filters.map(({tagTitle}) => tagTitle);
		  setFilterGroups(groups)
		  setFilterItems(makeFiltersArray(data.filters, validDeals));
	  })

    });


  }, []);

	useEffect(()=>{
		const deals = [...dealsList]
        sortBy === CONSTANTS.DEALS.DATE_ADDED
      ? deals.sort((a, b) => new Date(b[sortBy]) - new Date(a[sortBy]))
      : deals.sort((a, b) => new Date(a[sortBy]) - new Date(b[sortBy]));
		setDealsList(deals)
	},[sortBy]);
	
	const hideAemCardTitle = (validDeals) => {
		if (validDeals.length === 0 && is_author !=='') {
			const aemAutomaticCardTitle = dealListRef.current.parentNode.previousElementSibling;
			if (aemAutomaticCardTitle.classList.contains('aemcard')){
				aemAutomaticCardTitle.style.display = 'none';
			}
		}
	};

	useEffect(()=>{
		const matches = dealsList.filter(deal => shouldShowDeal(deal))
		setDealsThatMatchFilters(matches)
	},[filterItems, dealsList])

	const dealsPerPage = 12

	const dealsToDisplay = ()=>{
		const startIndex = (currentPage - 1) * dealsPerPage;
		const endIndex   = (currentPage -1) * dealsPerPage + dealsPerPage
		return dealsThatMatchFilters.slice(startIndex, endIndex).slice(0, product_page ? MAX_CARDS_PER_PRODUCT_PAGE:undefined);
	}

	function makeFiltersArray (filters, validDeals) {
		const filtersCopy = [...filters]
		const filtersCombined = []

		filtersCopy.forEach(filterGroup => {
			let group =  filterGroup.tagTitle;
			filterGroup.tags.forEach(tag => {
				const isDisabled = validDeals?.filter(deal=>deal.tags.includes(tag.tagId)).length === 0
				tag.checked = false
				tag.group = group
				tag.id = tag.tagId
				tag.label = tag.tagTitle
				tag.disabled = isDisabled
				filtersCombined.push(tag)
			})
		})

		return filtersCombined
	}


	const shouldShowDeal = (deal)=>{
		const checkedFilters = filterItems.filter(({checked}) => checked)
		const checkedFiltersSortedByGroup = {}
		let dealTagsSortedByGroup = {}

		checkedFilters.forEach(f=>{
			if(checkedFiltersSortedByGroup[f.group]){
				checkedFiltersSortedByGroup[f.group] = [...checkedFiltersSortedByGroup[f.group], f.tagId]
				return
			}

			checkedFiltersSortedByGroup[f.group] = [f.tagId]
		})

		deal.tags.forEach(tag => {
			const [match] = filterItems.filter(item => item.tagId === tag)
			const category = match?.group	
			if(dealTagsSortedByGroup[category]){
				dealTagsSortedByGroup[category] = [...dealTagsSortedByGroup[category], tag]
			} else {
				dealTagsSortedByGroup[category] = [tag]
			}
			dealTagsSortedByGroup[category] = Array.from(new Set(dealTagsSortedByGroup[category]))
		})

		return doesDealMatchFilters(dealTagsSortedByGroup, checkedFiltersSortedByGroup)
	}

	const [selectedFiltersCount, setSelectedFiltersCount] = useState({})

	const filtersActive = useMemo(() => {
		return filterItems.filter((item) => item.checked === true);
	  }, [filterItems]);

	  const resetFilterCount = () => {
		const selectedFiltersInitial = {}
		filterGroups.forEach(group => selectedFiltersInitial[group] = 0)
		setSelectedFiltersCount(selectedFiltersInitial)
	  }

	  const handleClearFilter = () => {
		setFilterItems((prevState) => 
		  prevState.map((item) => ({ ...item, checked: false }))
		);
		resetFilterCount();
	  };

	  const resetCountOnPillClick = item => {
		setSelectedFiltersCount(prev => {
		  const newCountsObject = {...prev};
		  newCountsObject[item.group] -= 1;
		  return newCountsObject;
		});
	  };

	  const handleFilters = (itemSelected) => {
		setFilterItems((prevState) =>
		  prevState.map((item) => {
			return item.id === itemSelected.tagId || item.id === itemSelected.value
			  ? { ...item, checked: !item.checked }
			  : { ...item }}
		  )
		);
	
		if(itemSelected.group) resetCountOnPillClick(itemSelected)
	  };

		return (
			<div
				className={classNames("deals-list", {
					"deals-list--with-title": title !== undefined, // class added when componet's title is set
				})}
				ref={dealListRef}
    	>

				<div className="bsp-travel-container" ref={containerTopRef}>
					<div className="row">
						<div className="col-xl-9 offset-xl-3">
							{title !== undefined && <h2 className="deals-list__heading">{title}</h2>}
							{!isDesktop && (
								<p className="deals-list__results-count">
									{`${dealsThatMatchFilters.length} ${results_label}`}
								</p>
							)}
						</div>
					</div>
					<div className="row">
						{ !product_page && (
							<div className="col-xl-3">
								{isDesktop && (
									<>
										<Filters
											filterItems={filterItems}
											filterGroups={filterGroups}
											title_modal={filter_modal_title}
											title={filter_title}
											button_mobile={filter_button_mobile}
											filter_clear={filter_clear}
											filter_show_all={filter_show_all}
											handleClearFilter={handleClearFilter}
											selectedFiltersCount={selectedFiltersCount}
											setSelectedFiltersCount={setSelectedFiltersCount}
											handleFilters={handleFilters}
											resetFilterCount={resetFilterCount}
										/>
										{showPackagesWidget && (
											<PackagesWidget
												json_url={json_cards_url}
												isSmallWidget={hasSmallWidget}
												isCarRentalEnabled={false}
											/>
										)}	
									</>
								)}
							</div>
						)}
						<div className={`col-xl-${product_page ? 12 : 9}`}>
							{ !product_page && (
								<div className="row justify-content-between">
									<div className="col-auto">
										{isDesktop ? (
											<p className="deals-list__results-count">
												{`${dealsThatMatchFilters.length} ${results_label}`}
											</p>
										) : (
											<Filters
												filterItems={filterItems}
												filterGroups={filterGroups}
												title_modal={filter_modal_title}
												title={filter_title}
												button_mobile={filter_button_mobile}
												filter_clear={filter_clear}
												filter_show_all={filter_show_all}
												numberOfResults = {dealsThatMatchFilters.length}
												handleClearFilter={handleClearFilter}
												selectedFiltersCount={selectedFiltersCount}
												setSelectedFiltersCount={setSelectedFiltersCount}
												handleFilters={handleFilters}
												resetFilterCount={resetFilterCount}
											/>
										)}
									</div>
									<div className="col-auto sort-wrapper">
										{isDesktop ? (
											<InLineDropdown
												label={sort_label}
												activeLabel={sortBy}
												selectedValueCallBack={setSortBy}
												options={[
													{
														key: "0_recent",
														value: "dateAdded",
														text: sort_recent,
													},
													{
														key: "1_expiry",
														value: "expiryDateObj",
														text: sort_expire,
													},
												]}
											/>
										) : (
											<Sort
												label={sort_label}
												recent={sort_recent}
												aboutToExpire={sort_expire}
												setSortBy={setSortBy}
												sortBy={sortBy}
												buttonText={sort_modal_button}
											/>
										)}
									</div>
								</div>
							)}
							{showPackagesWidget && !isDesktop && (
								<PackagesWidget
									json_url={json_cards_url}
									isSmallWidget
									
							/>
							)}
							{ !product_page && (
								<div className="row">
									<Pills
										pillItems={filtersActive}
										onClearPills={handleClearFilter}
										onClickPill={handleFilters}
									/>
								</div>
							)}
							{dealsList.length > 0 && (
								<div className={`row gx-0 gx-md-4 py-4 ${ product_page ? 
									((variant == CONSTANTS.CARD_VARIANTS.DEAL && dealsList.length > 3 ) || 
									(variant != CONSTANTS.CARD_VARIANTS.DEAL && dealsList.length > 1 ))
									? 'justify-content-left'
									: 'justify-content-center' : 'justify-content-left'
								}`}>
									{dealsToDisplay().map((item, index) => (
										<div className={`col-md-6 col-xl-${product_page ? 3 : 4}`} >
											<Card item={item} key={index}  />
										</div>
									))}
								</div>
							)}
						</div>
					</div>
				    { !product_page && (
						<Pagination
							pageCount={
								dealsThatMatchFilters.length > dealsPerPage
									? Math.ceil(dealsThatMatchFilters.length / dealsPerPage)
									: 1
							}
							marginPagesDisplayed={1}
							onPageChange={(props) => setCurrentPage(props)}
							containerTopRef={containerTopRef}
							currentPage={currentPage}
							pagination_background_color={pagination_background_color}
						/>
				    )}
				</div>
			</div>
		);
}

export default DealsList;
