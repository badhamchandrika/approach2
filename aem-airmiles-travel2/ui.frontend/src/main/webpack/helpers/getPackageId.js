import CONSTANTS from "../constants";

export const getPackageId = ({
  clustersPackages,
  collectorsClusterName,
  word,
  redemption = CONSTANTS.MILES,
  tripType=CONSTANTS.FORMPOST_TRIP_TYPE_ROUND
}) => {
  const re = new RegExp(word, "i");
  const filtered = clustersPackages.filter(
    (clusterPackage) =>
      clusterPackage.clusterName === collectorsClusterName &&
      re.test(clusterPackage.packageName) &&
      clusterPackage.redemption === redemption &&
      clusterPackage.tripType === tripType
  );
  return filtered[0]?.packageID;
};
