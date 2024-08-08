import { Component, OnInit } from '@angular/core';
import { Robot } from '../admin/common/robot';
import { RobotService } from '../admin/service/robot.service';

@Component({
  selector: 'app-robot',
  templateUrl: './robot.component.html',
  styleUrls: ['./robot.component.css']
})
export class RobotComponent implements OnInit{

  robots: Robot[] = []

  constructor(private robotService: RobotService) {}
  
  ngOnInit(): void {
    this.robotService.getRobots().subscribe(data=>{
      this.robots = data
    })
  }

  updateRobot() {
    for (let i =0; i<this.robots.length; i++ ){
      this.robotService.updateRobot(this.robots[i])
    }
  }


}
