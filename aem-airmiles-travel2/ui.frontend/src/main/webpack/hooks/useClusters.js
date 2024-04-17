import { useEffect, useState } from "react";
import { getClustersPackagesJson } from "../api/services";


const useClusters = () => {
  const [clustersPackages, setClustersPackages] = useState();
  useEffect(() => {
    getClustersPackagesJson()
      .then((json) => {
        setClustersPackages(json.data);
      })
      .catch((err) => console.log("err: ", err));
  }, []);

  return [clustersPackages];
};

export default useClusters;
