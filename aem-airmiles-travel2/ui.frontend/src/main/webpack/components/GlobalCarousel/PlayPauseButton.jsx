import React from 'react';
import CONSTANTS from '../../constants';

function PlayPauseButton({ type, isHovered }) {
  if (type === 'pause')
    return (
      <svg
        width="8px"
        height="14px"
        viewBox="0 0 20 25"
        version="1.1"
        xmlns="http://www.w3.org/2000/svg"
      >
        <g
          id="Page-1"
          stroke="none"
          strokeWidth="1"
          fill={isHovered ? CONSTANTS.COLORS.STEEL_900 : CONSTANTS.COLORS.SAPPHIRE_900}
          fillRule="evenodd"
        >
          <g
            id="Play-and-Pause-Icons"
            transform="translate(-233.000000, -92.000000)"
            fill={isHovered ? CONSTANTS.COLORS.STEEL_900 : CONSTANTS.COLORS.SAPPHIRE_900}
          >
            <g
              id="Group"
              transform="translate(233.641791, 92.323077)"
              fill={isHovered ? CONSTANTS.COLORS.STEEL_900 : CONSTANTS.COLORS.SAPPHIRE_900}
            >
              <rect
                id="Rectangle-1"
                x="-1.687539e-13"
                y="4.08562073e-14"
                width="6.89552239"
                height="24.3692308"
                rx="1"
              ></rect>
              <rect
                id="Rectangle-2"
                x="11.8208955"
                y="4.08562073e-14"
                width="6.89552239"
                height="24.3692308"
                rx="1"
              ></rect>
            </g>
          </g>
        </g>
      </svg>
    );

  if (type === 'play')
    return (
      <svg
        width="8px"
        height="14px"
        viewBox="0 0 21 20"
        version="1.1"
        xmlns="http://www.w3.org/2000/svg"
      >
        <g
          id="Page-1"
          stroke="none"
          strokeWidth="1"
          fill={isHovered ? CONSTANTS.COLORS.STEEL_900 : CONSTANTS.COLORS.SAPPHIRE_900}
          fillRule="evenodd"
        >
          <g
            id="Play-and-Pause-Icons"
            transform="translate(-89.000000, -92.000000)"
            fill={isHovered ? CONSTANTS.COLORS.STEEL_900 : CONSTANTS.COLORS.SAPPHIRE_900}
          >
            <polygon
              id="Triangle"
              transform="translate(101.000000, 104.000000) rotate(90.000000) translate(-101.000000, -104.000000) "
              points="101 92 113 116 89 116"
            ></polygon>
          </g>
        </g>
      </svg>
    );
}

export default PlayPauseButton;
