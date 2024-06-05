import { Component, OnInit } from '@angular/core';
import { RobotService } from '../../service/robot.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

	ultrasonic_data: number[] = []	

  constructor(private robotService: RobotService) {}

  ngOnInit(): void {
		this.robotService.getUltrasonicData(1).subscribe(data=>{
			this.ultrasonic_data = data;
		})
  }

  title = 'angular17ssrapp';
	chartOptions = {
	  zoomEnabled: true,
	  exportEnabled: true,
	  theme: "light2",
	  title: {
		text: "Ultrasonic Data"
	  },
	  data: [{
		type: "line",
		dataPoints: this.ultrasonic_data
	  }]
	}

  chartOptionsPie = {
	  animationEnabled: true,
	  theme: "dark2",
	  exportEnabled: true,
	  title: {
		  text: "Orders Chart"
	  },
	  subtitles: [{
		  
	  }],
	  data: [{
		  type: "pie", //change type to column, line, area, doughnut, etc
		  indexLabel: "{name}: {y}%",
		  dataPoints: [
		  	{ name: "Overhead", y: 9.1 },
		  	{ name: "Problem Solving", y: 3.7 },
		  	{ name: "Debugging", y: 36.4 },
		  	{ name: "Writing Code", y: 30.7 },
		  	{ name: "Firefighting", y: 20.1 }
		  ]
	  }]
	}
}
