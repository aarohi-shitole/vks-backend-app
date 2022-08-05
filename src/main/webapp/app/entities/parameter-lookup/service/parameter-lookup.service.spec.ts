import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IParameterLookup } from '../parameter-lookup.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../parameter-lookup.test-samples';

import { ParameterLookupService, RestParameterLookup } from './parameter-lookup.service';

const requireRestSample: RestParameterLookup = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('ParameterLookup Service', () => {
  let service: ParameterLookupService;
  let httpMock: HttpTestingController;
  let expectedResult: IParameterLookup | IParameterLookup[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ParameterLookupService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ParameterLookup', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const parameterLookup = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(parameterLookup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ParameterLookup', () => {
      const parameterLookup = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(parameterLookup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ParameterLookup', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ParameterLookup', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ParameterLookup', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addParameterLookupToCollectionIfMissing', () => {
      it('should add a ParameterLookup to an empty array', () => {
        const parameterLookup: IParameterLookup = sampleWithRequiredData;
        expectedResult = service.addParameterLookupToCollectionIfMissing([], parameterLookup);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(parameterLookup);
      });

      it('should not add a ParameterLookup to an array that contains it', () => {
        const parameterLookup: IParameterLookup = sampleWithRequiredData;
        const parameterLookupCollection: IParameterLookup[] = [
          {
            ...parameterLookup,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addParameterLookupToCollectionIfMissing(parameterLookupCollection, parameterLookup);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ParameterLookup to an array that doesn't contain it", () => {
        const parameterLookup: IParameterLookup = sampleWithRequiredData;
        const parameterLookupCollection: IParameterLookup[] = [sampleWithPartialData];
        expectedResult = service.addParameterLookupToCollectionIfMissing(parameterLookupCollection, parameterLookup);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(parameterLookup);
      });

      it('should add only unique ParameterLookup to an array', () => {
        const parameterLookupArray: IParameterLookup[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const parameterLookupCollection: IParameterLookup[] = [sampleWithRequiredData];
        expectedResult = service.addParameterLookupToCollectionIfMissing(parameterLookupCollection, ...parameterLookupArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const parameterLookup: IParameterLookup = sampleWithRequiredData;
        const parameterLookup2: IParameterLookup = sampleWithPartialData;
        expectedResult = service.addParameterLookupToCollectionIfMissing([], parameterLookup, parameterLookup2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(parameterLookup);
        expect(expectedResult).toContain(parameterLookup2);
      });

      it('should accept null and undefined values', () => {
        const parameterLookup: IParameterLookup = sampleWithRequiredData;
        expectedResult = service.addParameterLookupToCollectionIfMissing([], null, parameterLookup, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(parameterLookup);
      });

      it('should return initial array if no ParameterLookup is added', () => {
        const parameterLookupCollection: IParameterLookup[] = [sampleWithRequiredData];
        expectedResult = service.addParameterLookupToCollectionIfMissing(parameterLookupCollection, undefined, null);
        expect(expectedResult).toEqual(parameterLookupCollection);
      });
    });

    describe('compareParameterLookup', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareParameterLookup(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareParameterLookup(entity1, entity2);
        const compareResult2 = service.compareParameterLookup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareParameterLookup(entity1, entity2);
        const compareResult2 = service.compareParameterLookup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareParameterLookup(entity1, entity2);
        const compareResult2 = service.compareParameterLookup(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
