import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMemberBank } from '../member-bank.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../member-bank.test-samples';

import { MemberBankService, RestMemberBank } from './member-bank.service';

const requireRestSample: RestMemberBank = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('MemberBank Service', () => {
  let service: MemberBankService;
  let httpMock: HttpTestingController;
  let expectedResult: IMemberBank | IMemberBank[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MemberBankService);
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

    it('should create a MemberBank', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const memberBank = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(memberBank).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MemberBank', () => {
      const memberBank = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(memberBank).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MemberBank', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MemberBank', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MemberBank', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMemberBankToCollectionIfMissing', () => {
      it('should add a MemberBank to an empty array', () => {
        const memberBank: IMemberBank = sampleWithRequiredData;
        expectedResult = service.addMemberBankToCollectionIfMissing([], memberBank);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(memberBank);
      });

      it('should not add a MemberBank to an array that contains it', () => {
        const memberBank: IMemberBank = sampleWithRequiredData;
        const memberBankCollection: IMemberBank[] = [
          {
            ...memberBank,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMemberBankToCollectionIfMissing(memberBankCollection, memberBank);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MemberBank to an array that doesn't contain it", () => {
        const memberBank: IMemberBank = sampleWithRequiredData;
        const memberBankCollection: IMemberBank[] = [sampleWithPartialData];
        expectedResult = service.addMemberBankToCollectionIfMissing(memberBankCollection, memberBank);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(memberBank);
      });

      it('should add only unique MemberBank to an array', () => {
        const memberBankArray: IMemberBank[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const memberBankCollection: IMemberBank[] = [sampleWithRequiredData];
        expectedResult = service.addMemberBankToCollectionIfMissing(memberBankCollection, ...memberBankArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const memberBank: IMemberBank = sampleWithRequiredData;
        const memberBank2: IMemberBank = sampleWithPartialData;
        expectedResult = service.addMemberBankToCollectionIfMissing([], memberBank, memberBank2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(memberBank);
        expect(expectedResult).toContain(memberBank2);
      });

      it('should accept null and undefined values', () => {
        const memberBank: IMemberBank = sampleWithRequiredData;
        expectedResult = service.addMemberBankToCollectionIfMissing([], null, memberBank, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(memberBank);
      });

      it('should return initial array if no MemberBank is added', () => {
        const memberBankCollection: IMemberBank[] = [sampleWithRequiredData];
        expectedResult = service.addMemberBankToCollectionIfMissing(memberBankCollection, undefined, null);
        expect(expectedResult).toEqual(memberBankCollection);
      });
    });

    describe('compareMemberBank', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMemberBank(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMemberBank(entity1, entity2);
        const compareResult2 = service.compareMemberBank(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMemberBank(entity1, entity2);
        const compareResult2 = service.compareMemberBank(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMemberBank(entity1, entity2);
        const compareResult2 = service.compareMemberBank(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
