export const baseContract = {
  pageCount: 3, // Part of aem-react App
  marginPagesDisplayed: 1,
  onPageChange: props => console.log(props), // CallBack
  isMobile: true,
};

export const contract = {
  Large: {
    isMobile: false,
  },
  Medium: {
    isMobile: false,
  },
  Small: baseContract,
};
