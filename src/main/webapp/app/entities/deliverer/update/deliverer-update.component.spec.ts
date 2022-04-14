import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DelivererService } from '../service/deliverer.service';
import { IDeliverer, Deliverer } from '../deliverer.model';
import { ICooperative } from 'app/entities/cooperative/cooperative.model';
import { CooperativeService } from 'app/entities/cooperative/service/cooperative.service';

import { DelivererUpdateComponent } from './deliverer-update.component';

describe('Deliverer Management Update Component', () => {
  let comp: DelivererUpdateComponent;
  let fixture: ComponentFixture<DelivererUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let delivererService: DelivererService;
  let cooperativeService: CooperativeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DelivererUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(DelivererUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DelivererUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    delivererService = TestBed.inject(DelivererService);
    cooperativeService = TestBed.inject(CooperativeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Cooperative query and add missing value', () => {
      const deliverer: IDeliverer = { id: 456 };
      const cooperative: ICooperative = { id: 88025 };
      deliverer.cooperative = cooperative;

      const cooperativeCollection: ICooperative[] = [{ id: 36524 }];
      jest.spyOn(cooperativeService, 'query').mockReturnValue(of(new HttpResponse({ body: cooperativeCollection })));
      const additionalCooperatives = [cooperative];
      const expectedCollection: ICooperative[] = [...additionalCooperatives, ...cooperativeCollection];
      jest.spyOn(cooperativeService, 'addCooperativeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ deliverer });
      comp.ngOnInit();

      expect(cooperativeService.query).toHaveBeenCalled();
      expect(cooperativeService.addCooperativeToCollectionIfMissing).toHaveBeenCalledWith(cooperativeCollection, ...additionalCooperatives);
      expect(comp.cooperativesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const deliverer: IDeliverer = { id: 456 };
      const cooperative: ICooperative = { id: 84371 };
      deliverer.cooperative = cooperative;

      activatedRoute.data = of({ deliverer });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(deliverer));
      expect(comp.cooperativesSharedCollection).toContain(cooperative);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Deliverer>>();
      const deliverer = { id: 123 };
      jest.spyOn(delivererService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ deliverer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: deliverer }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(delivererService.update).toHaveBeenCalledWith(deliverer);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Deliverer>>();
      const deliverer = new Deliverer();
      jest.spyOn(delivererService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ deliverer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: deliverer }));
      saveSubject.complete();

      // THEN
      expect(delivererService.create).toHaveBeenCalledWith(deliverer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Deliverer>>();
      const deliverer = { id: 123 };
      jest.spyOn(delivererService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ deliverer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(delivererService.update).toHaveBeenCalledWith(deliverer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCooperativeById', () => {
      it('Should return tracked Cooperative primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCooperativeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
