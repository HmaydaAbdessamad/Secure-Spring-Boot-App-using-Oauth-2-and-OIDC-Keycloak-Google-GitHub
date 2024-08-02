import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {Product} from "../model/Product";

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [HttpClientModule],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent implements OnInit{

  products:Product[]=[]
  constructor(private http:HttpClient) {
  }
  ngOnInit() {
  this.http.get<Product[]>("http://localhost:8082/products").subscribe({
   next:(data:Product[])=>{
     this.products=data
   }
  })
  }
}
