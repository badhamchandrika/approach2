import { useEffect, useState } from "react";

const useFetch = (getData) => {
  const [data, setData] = useState();

  useEffect(() => {
    getData()
      .then((json) => {
        setData(json.data);
      })
      .catch((err) => console.log("err: ", err));
  }, []);

  return {data};
};

export default useFetch;
