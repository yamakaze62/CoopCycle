import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { OrderContentService } from '../service/order-content.service';

import { OrderContentComponent } from './order-content.component';

describe('OrderContent Management Component', () => {
  let comp: OrderContentComponent;
  let fixture: ComponentFixture<OrderContentComponent>;
  let service: OrderContentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [OrderContentComponent],
    })
      .overrideTemplate(OrderContentComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrderContentComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(OrderContentService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.orderContents?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
