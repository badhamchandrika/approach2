import {rotate90} from "2d-array-rotation";

/**
 * Takes an array of rooms and returns an object full of strings that can 
 * be used to indicate ages in a trisept formpost (e.g., "3|12|2")
 * 
 * @param {room[]} rooms 
 * 
 * 
 */

export const makeGsAgesObject = rooms => {  
    let mostChildren = rooms[0].children.length;
    rooms.forEach(room => {
        if(room.children.length > mostChildren) {
            mostChildren = room.children.length;
        }
    });
   const ages = new Array(rooms.length);
   ages.fill([]);
   ages.forEach(age => {
            age.length = mostChildren;
            age.fill(''); 
        }
    ); 
    const newAges = ages.map((a, i, arr)=>{
        const copy = [...a];
        for (let j = 0; j < copy.length; j++) {
            const element = rooms[i].children[j]; 
            if(element === undefined) {continue;}
            copy[j] = element;
        }
        return copy;
    });
    
    const rotatedAges = rotate90(newAges.reverse());
    const gsAges = {};
    rotatedAges.forEach((r, i) =>{ 
        gsAges[`gsAge${i+1}`] = r.join('|');
    });

    return gsAges;
};
