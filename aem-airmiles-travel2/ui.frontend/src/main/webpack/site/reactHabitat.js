import ReactHabitat from 'react-habitat';
import TestComp from '../components/ReactExample/Component';
import FlightsWidget from '../components/flightswidget/flightswidget.jsx';
import StaysWidget from '../components/stayswidget/stayswidget.jsx';
import CarRentalWidget from '../components/carrentalwidget/carrentalwidget.jsx';
import ActivitiesWidget from '../components/activitieswidget/activitieswidget.jsx';
import PackagesWidget from '../components/packageswidget/packageswidget.jsx';
import Filters from '../components/ui/Filters/Filters';
import CarouselWrapper from '../components/GlobalCarousel/CarouselWrapper.jsx';
import DealsList from '../components/dealslist/dealslist'; 
import BundlingWidget from '../components/bundlingwidget/BundlingWidget.jsx';


class App extends ReactHabitat.Bootstrapper {
 
	constructor() { 
		super();
 
		// Create a new container
		const containerBuilder = new ReactHabitat.ContainerBuilder();

		// Register our components that we want to expose to the DOM
		containerBuilder.register(TestComp).as('testcomp');
		containerBuilder.register(FlightsWidget).as('flightswidget');
		containerBuilder.register(StaysWidget).as('stayswidget');
		containerBuilder.register(CarRentalWidget).as('carrentalwidget');
		containerBuilder.register(ActivitiesWidget).as('activitieswidget');
		containerBuilder.register(BundlingWidget).as('bundlingwidget');
		containerBuilder.register(PackagesWidget).as('packageswidget');
		containerBuilder.register(Filters).as('filtercomponent');
		containerBuilder.register(CarouselWrapper).as('globalcarousel');
		containerBuilder.register(DealsList).as('dealslist');

		// Set the DOM container
		this.setContainer(containerBuilder.build());

	}
}

// Create a single instance of our app
const instance = new App();

// Bind the update method onto the window for the dynamic demo
// Alternatively we could have imported this file into another
// eg
//
// import App from './App';
//
// App.update();
//
window.updateHabitat = instance.update.bind(instance);

export default instance;
