export const higherPrecedenceCluster = (clusters) => {
  const max = clusters.reduce((prev, current) =>
    prev.precedence > current.precedence ? prev : current
  );
  return max;
};
