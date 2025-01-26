import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { EmployeeService } from '../employee.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-edit-employee',
  templateUrl: './edit-employee.component.html',
  styleUrls: ['./edit-employee.component.scss']
})
export class EditEmployeeComponent implements OnInit {

  employeeForm: any;

  empData = { id: '', name: '', salary: '', department: '' };

  constructor(
    private fb: FormBuilder,
    private employeeService: EmployeeService,
    private router: Router,
    private route: ActivatedRoute
  ) {

    this.employeeForm = fb.group({
      name: ['', Validators.required],
      salary: ['', Validators.required],
      department: ['', Validators.required]
    });

  }
  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.employeeService.getEmployeeById(id).subscribe((data) => {
      this.empData = data;
    });

  }

  updateEmployee() {
    this.employeeService.updateEmployee(this.empData).subscribe(() => {
      this.router.navigate(['/']);
    });
  }


}
