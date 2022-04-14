import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrderContentDetailComponent } from './order-content-detail.component';

describe('OrderContent Management Detail Component', () => {
  let comp: OrderContentDetailComponent;
  let fixture: ComponentFixture<OrderContentDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrderContentDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ orderContent: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(OrderContentDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OrderContentDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load orderContent on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.orderContent).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
