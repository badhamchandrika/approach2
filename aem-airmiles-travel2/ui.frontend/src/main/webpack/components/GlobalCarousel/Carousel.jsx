import React from 'react';
import PropTypes from 'prop-types';
import Grid from '@loyaltyone-am/grid';
import styled from 'styled-components';
import Slider from 'react-slick'; 
import CarouselControls from './CarouselControls.jsx'; 
import cn from 'classnames';
import { CarouselHeading } from './components';
import { isCurrentDateInLocalizedRange, hasLocalizedDatePassed } from '../../site/js/utils.js';

  
const TextContainer = styled.div`
  background-color: ${props => props.bgColor};
`;

class GlobalCarousel extends React.Component {
  constructor(props) {
    super(props);
    this.slides = props.contract.carouselSlides;
    this.state = {
      nav1: null,
      nav2: null,
      slide: 1,
      slideColor: props.contract.carouselSlides[0]?.bgColor,
      elapsedTime: 0,
      isSliderPlaying: true,
    };
    this.settings = {
      arrows: false,
      autoplaySpeed: 10000,
    };
    this.renderDate = new Date();

    const ctaRefs=[]
    props.contract.carouselSlides.forEach(() => ctaRefs.push(React.createRef()))
    this.ctaRefs = ctaRefs
  } 
 
 
  handleSlideAction = action => { 
    const actions = { 
      next: () => { 
        this.slider1.slickNext(); 
        this.slider2.slickNext();
      },
      prev: () => {
        this.slider1.slickPrev();
        this.slider2.slickPrev();
      },
      pause: () => {
        this.setState({
          ...this.state,
          isPlaying: false,
        });
      },
      play: () => {
        this.slider1.slickPlay();
        this.slider2.slickPlay();
        this.setState({
          ...this.state,
          isPlaying: true,
        });
      },
    };

    actions[action]();
  };

  getSlideColor = (_, i) => {
    this.setState({
      ...this.state,
      slideColor: this.slides[i].bgColor,
    });
  };

  componentDidMount() {
    this.setState({
      currentIntervalStartDate: new Date(),
    });
  }

  render() {

    const { contract } = this.props;
    const { imgRight, carouselSlides, dataTrackClick } = contract;
    const slidesFilteredByDate = carouselSlides.filter(slide => {
      const {startDate, endDate} = slide
      if(startDate && endDate) return isCurrentDateInLocalizedRange(startDate, endDate)
      if(startDate && !endDate) return hasLocalizedDatePassed(startDate) 
      if(!startDate && endDate) return !hasLocalizedDatePassed(endDate) 
      if(!startDate && !endDate) return true 
    })
    const {
      slide: slideIndex,
      slideColor,
      isPlaying,
      currentProgressPercentage,
    } = this.state;

    this.ctaRefs.forEach(ref => {
      if(ref.current === null) return
      const button = ref.current.querySelector('.CtaLinkV2__rebrand')
      if(!button) return
      button.addEventListener('keypress', e=> {
        if(e.key === ' ') e.target.click()
      })
    })

    return (
      <section className="carousel">
        <h3 className="carousel__aria-heading">Carousel</h3>
        <span className="carousel__aria-heading">
          {`Slide ${slideIndex + 1} of ${carouselSlides.length}`}
        </span>
        <div
          className={cn('carousel__inner', {
            'carousel__inner--imgRight': imgRight,
          })}
        >
          <Grid
            small={12}
            medium={6}
            large={6}
            className="carousel__text-container"
            style={{ 'background-color': slideColor }}
          >
            <div className="carousel__image-container" style={{ backgroundColor: slideColor }}>
              <Slider
                ref={slider => (this.slider1 = slider)}
                beforeChange={this.getSlideColor}
                afterChange={index =>
                  this.setState({ ...this.state, slide: index + 1 })
                }
                {...this.settings}
              >
                {slidesFilteredByDate.map((slide, i) => (
                  <a 
                    href={slide.slideImage.link} 
                    data-track-click={dataTrackClick} 
                    data-track-id={`${slide.dataTrackId}-${slide.description}-pos${i}`} 
                    data-track-impression={`${slide.dataTrackId}-${slide.description}-pos${i}`}
                  >
                    <img
                      className="carousel__img"
                      src={slide.slideImage.sources[0]}
                      alt={slide.slideImage.alt}
                    />
                  </a>
                ))} 
              </Slider>
            </div>
          </Grid>
          <Grid
            small={12}
            medium={6}
            large={6}
            className="carousel__text-container"
            style={{ 'background-color': 'black' }}
          >
            <TextContainer className="carousel__text-container-textdiv" bgColor={slideColor}>
              <Slider
                asNavFor={this.state.nav1}
                ref={slider => (this.slider2 = slider)}
                slidesToShow={1}
                fade
                {...this.settings}
              >  
                {slidesFilteredByDate.map((content, index) => (
                  <div>
                    <CarouselHeading className='carousel__slide-heading'>
                      {content.headerText}
                    </CarouselHeading>
                    <div 
                      dangerouslySetInnerHTML={{ __html: content.bodyText }}
                      className='carousel__content-text'
                    />
                    <div className="carousel__cta-container" ref={this.ctaRefs[index]}>
                      {content.actions.map((cta) => ( 
                        <a 
                          href={cta.linkUrl} 
                          className='cta-button'
                          target='_blank'
                          data-track-click={dataTrackClick}
                          data-track-id={`${content.dataTrackId}-${content.description}-pos${index}`}
                          tabIndex={index + 1 === this.state.slide ? 1 : -1}
                        >
                          {cta.text}
                        </a>
                      ))}
                    </div>
                  </div>
                ))}
              </Slider>
              {slidesFilteredByDate.length > 1 && (
                <CarouselControls
                  slides={slidesFilteredByDate.length}
                  slide={slideIndex}
                  interval={this.settings.autoplaySpeed}
                  handleSlideAction={this.handleSlideAction}
                  isPlaying={isPlaying}
                  time={currentProgressPercentage}
                  renderDate={this.renderDate}
                  slideColor={slideColor}
                  dataTrackClick={dataTrackClick}
                />
              )}
            </TextContainer>
          </Grid>
        </div>
      </section>
    );
  }
}

GlobalCarousel.propTypes = {
  contract: PropTypes.shape({
    imgRight: PropTypes.bool,
    carouselSlides: PropTypes.arrayOf(
      PropTypes.shape({
        slideImage: PropTypes.shape({
          alt: PropTypes.string,
          sources: PropTypes.arrayOf(PropTypes.string)
        }),
        png: PropTypes.shape({
          alt: PropTypes.string,
          sources: PropTypes.arrayOf(PropTypes.string) 
        }),
        imageLink: PropTypes.string,
        bgColor: PropTypes.string,
        headerText: PropTypes.string,
        headerColor: PropTypes.string,
        wayfinder: PropTypes.string,
        bodyText: PropTypes.string,
        actions: PropTypes.arrayOf(PropTypes.shape({
          text: PropTypes.string,
          ctaType: PropTypes.string,
          className: PropTypes.string,
          dataTestId: PropTypes.string,
          dataTrackId: PropTypes.string,
          dataTrackClick: PropTypes.string,
          ariaLabel: PropTypes.string,
          type: PropTypes.string,
          url: PropTypes.string,
        })) ,
        ctaColor: PropTypes.string
      })
    )
  }).isRequired,
}

export default GlobalCarousel;

