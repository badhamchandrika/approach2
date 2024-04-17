export function doesDealMatchFilters(productTags, selectedFilters) {
    if(!productTags || !selectedFilters) return true
    for (const [category, tags] of Object.entries(selectedFilters)) {
        if (!tags.some(tag => productTags[category]?.includes(tag))) {
            return false;
        }
    }
    return true;
}
