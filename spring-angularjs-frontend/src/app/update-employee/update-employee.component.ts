import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../employee.service';
import { Employee } from '../employee';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-update-employee',
  templateUrl: './update-employee.component.html',
  styleUrls: ['./update-employee.component.css']
})
export class UpdateEmployeeComponent implements OnInit {

  id: number;
  employee: Employee;
  submited = false;

  constructor(private route: ActivatedRoute, private employeeService: EmployeeService,
    private router: Router) { }

  ngOnInit() {
    this.employee = new Employee();
    this.id = this.route.snapshot.params['id']
  }

  save() {
    this.employeeService
      .createEmployee(this.employee)
      .subscribe(
        data => console.log(data),
        error => console.log(error)
      )
    this.employee = new Employee();
    this.gotoList();
  }

  onSubmit() {
    this.submited = true;
    this.save();
  }

  gotoList() {
    this.router.navigate(['/employees']);
  }
}
