import React, { useState, useEffect, useRef } from 'react';
import Icon from '@loyaltyone-am/icon';
import {
  CircularProgressbarWithChildren,
  buildStyles,
} from 'react-circular-progressbar';
import 'react-circular-progressbar/dist/styles.css';
import PlayPauseButton from './PlayPauseButton';
import { Button, ButtonWrapper, ButtonBorder } from './components';
import TypographyV3 from '@loyaltyone-am/typographyv3';
import { Types, Color } from '@loyaltyone-am/typographyv3/TypographyConstants';

const CarouselControls = ({
  handleSlideAction,
  interval,
  renderDate,
  slideColor,
  slides,
  slide,
  dataTrackClick
}) => {
  const freezeTime = useRef(null); // The last time at which the slider was paused. resets after each slide
  const isPlaying = useRef(true); // A bool used within the animation function to track whether slider is playing
  const isFocused = useRef(false); // whether or not the mouse is over the slider
  const elementInFocus = useRef(false); // whether or not an element in the slider has focus
  const cumulativeFreezeTime = useRef(0); // The amount of time spent paused on the current slide
  const timeEllapsed = useRef(null); // The total time ellapsed on the current slide
  const currentIntervalStartDate = useRef(renderDate); // The time at which the current slide began playing
  const requestRef = useRef(null); // ID of the current animation frame
  const carouselDiv = useRef(null);


  const [currentProgressPercentage, setCurrentProgressPercentage] = useState(0);
  const [isPlayingState, setIsPlayingState] = useState(true); // We need to track whether the slider is playing in state too, so that the play btn changes
  const [prevButtonIsFocused, setPrevButtonIsFocused] = useState(false);
  const [prevButtonIsHovered, setPrevButtonIsHovered] = useState(false);
  const [nextButtonIsFocused, setNextButtonIsFocused] = useState(false);
  const [nextButtonIsHovered, setNextButtonIsHovered] = useState(false);
  const [pauseButtonIsFocused, setPauseButtonIsFocused] = useState(false);
  const [pauseButtonIsHovered, setPauseButtonIsHovered] = useState(false);

  const onChange = () => {
    currentIntervalStartDate.current = new Date();
    setCurrentProgressPercentage(0);
    cumulativeFreezeTime.current = 0;
    freezeTime.current = null;
  };

  const onAnimationTick = () => {
    const now = new Date();
    const carouselDivChildren = Array.from(carouselDiv.current.querySelectorAll('*'))
    const focusedElements = carouselDivChildren.filter(el => 
      document.activeElement === el && el.classList.contains('pause-when-focused')
    )

    if(focusedElements.length > 0) elementInFocus.current = true; 
    if(focusedElements.length < 1) elementInFocus.current = false;

    /* If slider is not playing or is focused, and there's no freezetime, create one */
    if (!freezeTime.current && (!isPlaying.current || isFocused.current || elementInFocus.current)) {
      freezeTime.current = new Date();
    }

    /* When slider resumes playing or is unfocused, add the amount of time it was paused for to the
       cumulative freeze time */
    if (freezeTime.current && isPlaying.current && !(isFocused.current || elementInFocus.current)) {
      cumulativeFreezeTime.current += now.getTime() - freezeTime.current;
      freezeTime.current = null;
    }

    /* Calculate the amount of time since the current slide started, and subtract the time spent frozen
       since the current slide started */
    timeEllapsed.current =
      now.getTime() -
      currentIntervalStartDate.current.getTime() -
      cumulativeFreezeTime.current;

    const ellapsedPercentage = (timeEllapsed.current / interval) * 100;

    /* 1. If the slider is below 100%, set currentProgressPercentage to ellapsedPercentage. 
       2 .If ellapsedPercentage meets or exceeds 100%, go to the next slide and reset ellapsed percentage to zero. 
       3. if the slider isn't playing or is in focus, return without doing anything. */
    setCurrentProgressPercentage(() => {
      if (!isPlaying.current || isFocused.current || elementInFocus.current) return;
      if (ellapsedPercentage >= 100) {
        handleSlideAction('next');
        onChange();
        return 0;
      }

      return ellapsedPercentage;
    });

    /* save the animation frame ID so that we can use it in the useEffect cleanup function */
    requestRef.current = requestAnimationFrame(onAnimationTick);
  };

  useEffect(() => {
    if (slides < 2) return;

    carouselDiv.current = document.getElementsByClassName('carousel__inner')[0];

    carouselDiv.current.addEventListener('mouseenter', e => {
      isFocused.current = true;
    });

    carouselDiv.current.addEventListener('mouseleave', e => {
      isFocused.current = false;
    });

    onAnimationTick();

    return () => {
      cancelAnimationFrame(requestRef.current);
    };
  }, []);

  return (
    <div className="carousel__controls">
      <div
        style={{
          'boxShadow': prevButtonIsFocused ? 'inset 0px 0px 1px 2px #169BA2' : 'none',
          display: 'inline-flex'
        }}
      >
        <ButtonBorder>
          <Button
            onClick={() => {
              handleSlideAction('prev');
              onChange();
            }}
            data-track-click={dataTrackClick}
            data-track-id='previous-slide'
            aria-label='previous-slide'
            onFocus={()=>{setPrevButtonIsFocused(true)}}
            onBlur={()=>{setPrevButtonIsFocused(false)}}
            onMouseEnter={()=>setPrevButtonIsHovered(true)}
            onMouseLeave={()=>setPrevButtonIsHovered(false)}
            className='pause-when-focused'
          >
            <Icon color={prevButtonIsHovered ? "#1c2d3f" : "#1f68da"} type="functional-arrow-left"></Icon>
          </Button>
        </ButtonBorder>
      </div>
      <span className="carousel__slide-count">
          {slide}/{slides}
      </span>
      <ButtonWrapper 
        onMouseEnter={()=>setNextButtonIsHovered(true)}
        onMouseLeave={()=>setNextButtonIsHovered(false)}
      >
        <CircularProgressbarWithChildren
          maxValue={100}
          strokeWidth={5}
          value={currentProgressPercentage}
          styles={buildStyles({
            strokeLinecap: 'butt',
            pathColor: nextButtonIsHovered ? "#1c2d3f" : '#1f68da',
            trailColor: 'none',
            pathTransitionDuration: 0,
          })}
        >
          <div style={{
            'boxShadow': nextButtonIsFocused ? 'inset 0px 0px 1px 2px #169BA2' : 'none',
            padding: nextButtonIsFocused ? '8px' : 'none',
            display: 'inline-flex'
          }}>
          <Button
            onClick={() => {
              handleSlideAction('next');
              onChange();
            }}
            data-track-click={dataTrackClick}
            data-track-id='next-slide'
            aria-label='next-slide'
            onFocus={()=>{setNextButtonIsFocused(true)}}
            onBlur={()=>{setNextButtonIsFocused(false)}}
            className='pause-when-focused'
          >
            <Icon color={nextButtonIsHovered ? "#1c2d3f" : "#1f68da"} type="functional-arrow-right"></Icon>
          </Button>
          </div>
        </CircularProgressbarWithChildren>
      </ButtonWrapper>
        <div style={{
            'boxShadow': pauseButtonIsFocused ? 'inset 0px 0px 1px 2px #169BA2' : 'none',
            display: 'inline-flex'
          }}>
        <ButtonBorder>
          <Button
            color={slideColor}
            onClick={() => {
              isPlaying.current = !isPlaying.current;
              setIsPlayingState(!isPlayingState);
            }}
            onFocus={()=>setPauseButtonIsFocused(true)}
            onBlur={()=>setPauseButtonIsFocused(false)}
            onMouseEnter={()=>setPauseButtonIsHovered(true)}
            onMouseLeave={()=>setPauseButtonIsHovered(false)}
            data-track-click={dataTrackClick}
            data-track-id={isPlayingState ? 'pause' : 'play'}
            aria-label={isPlayingState ? 'pause' : 'play'}
          >
            <PlayPauseButton isHovered={pauseButtonIsHovered} type={isPlayingState ? 'pause' : 'play'}/>
          </Button>
        </ButtonBorder>
      </div>
    </div>
  );
};

export default CarouselControls;
