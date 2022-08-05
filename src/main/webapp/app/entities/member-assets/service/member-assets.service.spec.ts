import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMemberAssets } from '../member-assets.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../member-assets.test-samples';

import { MemberAssetsService, RestMemberAssets } from './member-assets.service';

const requireRestSample: RestMemberAssets = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('MemberAssets Service', () => {
  let service: MemberAssetsService;
  let httpMock: HttpTestingController;
  let expectedResult: IMemberAssets | IMemberAssets[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MemberAssetsService);
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

    it('should create a MemberAssets', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const memberAssets = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(memberAssets).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MemberAssets', () => {
      const memberAssets = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(memberAssets).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MemberAssets', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MemberAssets', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MemberAssets', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMemberAssetsToCollectionIfMissing', () => {
      it('should add a MemberAssets to an empty array', () => {
        const memberAssets: IMemberAssets = sampleWithRequiredData;
        expectedResult = service.addMemberAssetsToCollectionIfMissing([], memberAssets);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(memberAssets);
      });

      it('should not add a MemberAssets to an array that contains it', () => {
        const memberAssets: IMemberAssets = sampleWithRequiredData;
        const memberAssetsCollection: IMemberAssets[] = [
          {
            ...memberAssets,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMemberAssetsToCollectionIfMissing(memberAssetsCollection, memberAssets);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MemberAssets to an array that doesn't contain it", () => {
        const memberAssets: IMemberAssets = sampleWithRequiredData;
        const memberAssetsCollection: IMemberAssets[] = [sampleWithPartialData];
        expectedResult = service.addMemberAssetsToCollectionIfMissing(memberAssetsCollection, memberAssets);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(memberAssets);
      });

      it('should add only unique MemberAssets to an array', () => {
        const memberAssetsArray: IMemberAssets[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const memberAssetsCollection: IMemberAssets[] = [sampleWithRequiredData];
        expectedResult = service.addMemberAssetsToCollectionIfMissing(memberAssetsCollection, ...memberAssetsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const memberAssets: IMemberAssets = sampleWithRequiredData;
        const memberAssets2: IMemberAssets = sampleWithPartialData;
        expectedResult = service.addMemberAssetsToCollectionIfMissing([], memberAssets, memberAssets2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(memberAssets);
        expect(expectedResult).toContain(memberAssets2);
      });

      it('should accept null and undefined values', () => {
        const memberAssets: IMemberAssets = sampleWithRequiredData;
        expectedResult = service.addMemberAssetsToCollectionIfMissing([], null, memberAssets, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(memberAssets);
      });

      it('should return initial array if no MemberAssets is added', () => {
        const memberAssetsCollection: IMemberAssets[] = [sampleWithRequiredData];
        expectedResult = service.addMemberAssetsToCollectionIfMissing(memberAssetsCollection, undefined, null);
        expect(expectedResult).toEqual(memberAssetsCollection);
      });
    });

    describe('compareMemberAssets', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMemberAssets(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMemberAssets(entity1, entity2);
        const compareResult2 = service.compareMemberAssets(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMemberAssets(entity1, entity2);
        const compareResult2 = service.compareMemberAssets(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMemberAssets(entity1, entity2);
        const compareResult2 = service.compareMemberAssets(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
