import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMemberLandAssets } from '../member-land-assets.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../member-land-assets.test-samples';

import { MemberLandAssetsService, RestMemberLandAssets } from './member-land-assets.service';

const requireRestSample: RestMemberLandAssets = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('MemberLandAssets Service', () => {
  let service: MemberLandAssetsService;
  let httpMock: HttpTestingController;
  let expectedResult: IMemberLandAssets | IMemberLandAssets[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MemberLandAssetsService);
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

    it('should create a MemberLandAssets', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const memberLandAssets = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(memberLandAssets).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MemberLandAssets', () => {
      const memberLandAssets = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(memberLandAssets).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MemberLandAssets', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MemberLandAssets', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MemberLandAssets', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMemberLandAssetsToCollectionIfMissing', () => {
      it('should add a MemberLandAssets to an empty array', () => {
        const memberLandAssets: IMemberLandAssets = sampleWithRequiredData;
        expectedResult = service.addMemberLandAssetsToCollectionIfMissing([], memberLandAssets);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(memberLandAssets);
      });

      it('should not add a MemberLandAssets to an array that contains it', () => {
        const memberLandAssets: IMemberLandAssets = sampleWithRequiredData;
        const memberLandAssetsCollection: IMemberLandAssets[] = [
          {
            ...memberLandAssets,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMemberLandAssetsToCollectionIfMissing(memberLandAssetsCollection, memberLandAssets);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MemberLandAssets to an array that doesn't contain it", () => {
        const memberLandAssets: IMemberLandAssets = sampleWithRequiredData;
        const memberLandAssetsCollection: IMemberLandAssets[] = [sampleWithPartialData];
        expectedResult = service.addMemberLandAssetsToCollectionIfMissing(memberLandAssetsCollection, memberLandAssets);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(memberLandAssets);
      });

      it('should add only unique MemberLandAssets to an array', () => {
        const memberLandAssetsArray: IMemberLandAssets[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const memberLandAssetsCollection: IMemberLandAssets[] = [sampleWithRequiredData];
        expectedResult = service.addMemberLandAssetsToCollectionIfMissing(memberLandAssetsCollection, ...memberLandAssetsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const memberLandAssets: IMemberLandAssets = sampleWithRequiredData;
        const memberLandAssets2: IMemberLandAssets = sampleWithPartialData;
        expectedResult = service.addMemberLandAssetsToCollectionIfMissing([], memberLandAssets, memberLandAssets2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(memberLandAssets);
        expect(expectedResult).toContain(memberLandAssets2);
      });

      it('should accept null and undefined values', () => {
        const memberLandAssets: IMemberLandAssets = sampleWithRequiredData;
        expectedResult = service.addMemberLandAssetsToCollectionIfMissing([], null, memberLandAssets, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(memberLandAssets);
      });

      it('should return initial array if no MemberLandAssets is added', () => {
        const memberLandAssetsCollection: IMemberLandAssets[] = [sampleWithRequiredData];
        expectedResult = service.addMemberLandAssetsToCollectionIfMissing(memberLandAssetsCollection, undefined, null);
        expect(expectedResult).toEqual(memberLandAssetsCollection);
      });
    });

    describe('compareMemberLandAssets', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMemberLandAssets(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMemberLandAssets(entity1, entity2);
        const compareResult2 = service.compareMemberLandAssets(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMemberLandAssets(entity1, entity2);
        const compareResult2 = service.compareMemberLandAssets(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMemberLandAssets(entity1, entity2);
        const compareResult2 = service.compareMemberLandAssets(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
