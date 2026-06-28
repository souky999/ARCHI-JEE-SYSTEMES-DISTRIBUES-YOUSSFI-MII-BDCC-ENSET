import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product.model';

@Component({
  selector: 'app-products',
  imports: [CommonModule],
  templateUrl: './products.component.html'
})
export class ProductsComponent implements OnInit {
  products: Product[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getProducts().subscribe(products => {
      this.products = products;
    });
  }

  toggleSelected(product: Product): void {
    const newValue = !product.selected;
    this.productService.updateSelected(product.id, newValue).subscribe(updated => {
      product.selected = updated.selected;
    });
  }

  onDelete(product: Product): void {
    const confirmed = confirm(`Êtes-vous sûr de vouloir supprimer "${product.name}" ?`);
    if (!confirmed) {
      return;
    }
    this.productService.deleteProduct(product.id).subscribe(() => {
      this.products = this.products.filter(p => p.id !== product.id);
    });
  }
}
