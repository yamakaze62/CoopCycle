import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IOrderContent, OrderContent } from '../order-content.model';

import { OrderContentService } from './order-content.service';

describe('OrderContent Service', () => {
  let service: OrderContentService;
  let httpMock: HttpTestingController;
  let elemDefault: IOrderContent;
  let expectedResult: IOrderContent | IOrderContent[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OrderContentService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      idProduct: 0,
      idOrder: 0,
      quantityAsked: 0,
      productAvailable: false,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a OrderContent', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new OrderContent()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OrderContent', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          idProduct: 1,
          idOrder: 1,
          quantityAsked: 1,
          productAvailable: true,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OrderContent', () => {
      const patchObject = Object.assign(
        {
          idProduct: 1,
          quantityAsked: 1,
          productAvailable: true,
        },
        new OrderContent()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OrderContent', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          idProduct: 1,
          idOrder: 1,
          quantityAsked: 1,
          productAvailable: true,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a OrderContent', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addOrderContentToCollectionIfMissing', () => {
      it('should add a OrderContent to an empty array', () => {
        const orderContent: IOrderContent = { id: 123 };
        expectedResult = service.addOrderContentToCollectionIfMissing([], orderContent);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(orderContent);
      });

      it('should not add a OrderContent to an array that contains it', () => {
        const orderContent: IOrderContent = { id: 123 };
        const orderContentCollection: IOrderContent[] = [
          {
            ...orderContent,
          },
          { id: 456 },
        ];
        expectedResult = service.addOrderContentToCollectionIfMissing(orderContentCollection, orderContent);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OrderContent to an array that doesn't contain it", () => {
        const orderContent: IOrderContent = { id: 123 };
        const orderContentCollection: IOrderContent[] = [{ id: 456 }];
        expectedResult = service.addOrderContentToCollectionIfMissing(orderContentCollection, orderContent);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(orderContent);
      });

      it('should add only unique OrderContent to an array', () => {
        const orderContentArray: IOrderContent[] = [{ id: 123 }, { id: 456 }, { id: 37055 }];
        const orderContentCollection: IOrderContent[] = [{ id: 123 }];
        expectedResult = service.addOrderContentToCollectionIfMissing(orderContentCollection, ...orderContentArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const orderContent: IOrderContent = { id: 123 };
        const orderContent2: IOrderContent = { id: 456 };
        expectedResult = service.addOrderContentToCollectionIfMissing([], orderContent, orderContent2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(orderContent);
        expect(expectedResult).toContain(orderContent2);
      });

      it('should accept null and undefined values', () => {
        const orderContent: IOrderContent = { id: 123 };
        expectedResult = service.addOrderContentToCollectionIfMissing([], null, orderContent, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(orderContent);
      });

      it('should return initial array if no OrderContent is added', () => {
        const orderContentCollection: IOrderContent[] = [{ id: 123 }];
        expectedResult = service.addOrderContentToCollectionIfMissing(orderContentCollection, undefined, null);
        expect(expectedResult).toEqual(orderContentCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
