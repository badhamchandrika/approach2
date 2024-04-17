import React, {useState} from 'react';

function TestComp({title}) {
	const [count, setCount] = useState(0)

	function increment(){
		setCount(count + 1)
	}

	function decrement(){
		setCount(count - 1)
	}

	return (
		<div className='ReactTest'>
			<h3 className='ReactTest__title'>Hello, I'm being rendered with react!</h3>
			<p>And here's some text from AEM: <span dangerouslySetInnerHTML={{__html: title}}></span></p>
			<h6 className='ReactTest__count'>{count}</h6>
			<button className='ReactTest__btn' onClick={decrement}>Count Down</button>
			<button className='ReactTest__btn' onClick={increment}>Count Up</button>
		</div>
	);
}

export default TestComp;