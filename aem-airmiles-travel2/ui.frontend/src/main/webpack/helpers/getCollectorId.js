export const getCollectorId = () => {
  const cookies = document.cookie.split(";").map((cookie) => cookie.split("="));
  const cookiesObj = cookies.reduce(
    (accumulator, [key, value]) => ({
      ...accumulator,
      [key.trim()]: decodeURIComponent(value),
    }),
    {}
  );
  const memdeets = cookiesObj.memdeets;
  let parsedMemdeets = {};
  if (memdeets) {
    parsedMemdeets = JSON.parse(memdeets);
  }
  return parsedMemdeets.cardNumber;
};
