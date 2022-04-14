import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DelivererService } from '../service/deliverer.service';

import { DelivererComponent } from './deliverer.component';

describe('Deliverer Management Component', () => {
  let comp: DelivererComponent;
  let fixture: ComponentFixture<DelivererComponent>;
  let service: DelivererService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DelivererComponent],
    })
      .overrideTemplate(DelivererComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DelivererComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DelivererService);

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
    expect(comp.deliverers?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
