import React from 'react';
import Grid from '@loyaltyone-am/grid';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';

const CaourselContent = React.forwardRef(
  
  (
    { settings, pauseSlides, slideRef, playSlides, slides, handleClick },
    ref
  ) => {
    return (
      <Grid
        className="carousel__text-container"
        small={12}
        medium={5}
        large={6}
      >
        <Slider {...settings} ref={ref} asNavFor={slideRef()}>
          {slides.map(({ content }) => (
            <div onMouseEnter={pauseSlides} onMouseLeave={playSlides}>
              <h1>{content.title}</h1>
              <h2>{content.wayfinder}</h2>
              <p>{content.body}</p>
            </div>
          ))}
        </Slider>
        <button
          onClick={() => {
            handleClick('prev');
          }}
        >
          prev
        </button>
        <button onClick={() => handleClick('next')}>next</button>
      </Grid>
    );
  }
);

export default CaourselContent;
