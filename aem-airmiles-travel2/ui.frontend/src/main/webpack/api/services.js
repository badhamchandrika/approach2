import CONSTANTS from "../constants";
import { client } from "./client";

export const getAuthorConfig = (json_url) => client.get(json_url);

export const getFlightsJson = () =>
  client.get(`${CONSTANTS.JSONS_BASE_PATH}/flights/flights.json`);

export const getStaysJson = () =>
  client.get(`${CONSTANTS.JSONS_BASE_PATH}/stays/stays.json`);

export const getRentalsJson = () =>
  client.get(`${CONSTANTS.JSONS_BASE_PATH}/rentals/rentals.json`);

export const getPackagesJson = () =>
  client.get(`${CONSTANTS.JSONS_BASE_PATH}/packages/packages.json`);

export const getActivitiesJson = () =>
  client.get(`${CONSTANTS.JSONS_BASE_PATH}/activities/activities.json`);

export const getClustersPackagesJson = () =>
  client.get(`${CONSTANTS.JSONS_BASE_PATH}/clusters.json`);

export const getCollectorClusters = (collectorId, environment) => {
  const collectorClustersUrl =
    environment === "prod"
      ? "https://bff.api.airmiles.ca/dombff-contents/segment/collector-clusters/searches"
      : `https://${environment}-bff.api.airmiles.ca/dombff-contents/segment/collector-clusters/searches`;

  return client.post(
    collectorClustersUrl,
    {
      cardNumber: collectorId,
      channel: "TRAVEL",
    },
    {
      withCredentials: true,
      headers: {
        "Content-Type": "application/json",
        "x-origin-client": "internal:amrp:web",
        "Accept-Language": "en-CA",
      },
    }
  );
};
