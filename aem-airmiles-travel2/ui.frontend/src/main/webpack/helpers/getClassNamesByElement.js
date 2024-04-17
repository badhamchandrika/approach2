export const getClassNamesByElement = (
  classNameParams,
  elementClass,
  applyClasses = true
) =>
  Object.entries(classNameParams).reduce(
    (prev, [key, value]) => ({
      ...prev,
      [`${elementClass}--${key}`]: applyClasses ? value : false,
    }),
    {}
  );
