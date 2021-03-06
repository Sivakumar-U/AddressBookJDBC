package com.blz.addressbook.jdbc;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.blz.addressbook.jdbc.AddressBookService.IOService;

public class AddressBookTest {

	static AddressBookService addressBookService;

	@BeforeClass
	public static void createAddressBookObj() {
		addressBookService = new AddressBookService();
	}

	@Test
	public void givenAddressBookContactsInDB_WhenRetrieved_ShouldMatchContactsCount() throws AddressBookException {
		List<AddressBookData> addressBookData = addressBookService.readAddressBookData(IOService.DB_IO);
		Assert.assertEquals(5, addressBookData.size());
	}

	@Test
	public void givenAddressBook_WhenUpdate_ShouldSyncWithDB() throws AddressBookException {
		addressBookService.updateRecord("Jyo", "America");
		boolean result = addressBookService.checkUpdatedRecordSyncWithDatabase("Jyo");
		Assert.assertTrue(result);
	}

	@Test
	public void givenAddressBook_WhenRetrieved_ShouldMatchCountInGivenRange() throws AddressBookException {
		List<AddressBookData> addressBookData = addressBookService.readAddressBookData(IOService.DB_IO, "2018-02-14",
				"2020-06-02");
		Assert.assertEquals(1, addressBookData.size());
	}

	@Test
	public void givenAddresBook_WhenRetrieved_ShouldReturnCountOfCity() throws AddressBookException {
		Assert.assertEquals(1, addressBookService.readAddressBookData("count", "Tirupati"));
	}

	@Test
	public void givenAddresBookDetails_WhenAdded_ShouldSyncWithDB() throws AddressBookException {
		addressBookService.readAddressBookData(IOService.DB_IO);
		addressBookService.addNewContact("Siva", "Panapati", "Mcl", "Tirupati", "AP", "517586", "897562103",
				"sivap@gmail.com", "2020-11-22");
		boolean result = addressBookService.checkUpdatedRecordSyncWithDatabase("Siva");
		Assert.assertTrue(result);
	}

	@Test
	public void givenMultipleContact_WhenAdded_ShouldSyncWithDB() throws AddressBookException {
		AddressBookData[] contactArray = {
				new AddressBookData("Tharun", "Pulluru", "Mcl", "Tirupati", "AP", "517536", "8975658741",
						"tharun@gmail.com", "2020-11-23"),
				new AddressBookData("Nani", "Kalthireddy", "Mr palli", "Tirupati", "AP", "517533", "9874563201",
						"nani@gmail.com", "2020-11-23") };
		addressBookService.addMultipleContactsToDBUsingThreads(Arrays.asList(contactArray));
		boolean result1 = addressBookService.checkUpdatedRecordSyncWithDatabase("Tharun");
		boolean result2 = addressBookService.checkUpdatedRecordSyncWithDatabase("Nani");
		Assert.assertTrue(result1);
		Assert.assertTrue(result2);

	}
}
