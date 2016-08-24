package com.comp;


import com.comp.os.DetectorCompOS;

public class GetAllHWIDs {

	private static String allHWIDs = "";
	
	public static String getHWIDs () {
		// 1.CPU_id
		if(DetectorCompOS.isWindows())printIDs("CPU", CPUID.getCPUID());
		
		// 2.HDD_ids
		// 2.1.HDDID
		printIDs("HDDid", new HDDID().getHddID());
		// 2.2.HDDLDSN
        if(DetectorCompOS.isWindows())printIDs("HDDsnld", HDDID.getSerialNumberLogicDisk());

		// 3.MB_ids
		// 3.1.System_model (like MB_id)
		////printIDs("MBModel", MBID.getMBModel());
		// 3.2.BIOS_version
		////printIDs("MBBIOS", MBID.getMBBIOSVersion());

		// 4.Win_ids
		// 4.1.Win_product_key
		printIDs("WinProdKey", WinIDs.getWinProductKey());
		// 4.2.Win_licence_key
		printIDs("WinLicKey", WinIDs.getWinLicenceKey());
		// 4.3.Win_installation_date
		////printIDs("WinInstDay", WinIDs.getWinInstallDate());
		
		return allHWIDs;
	}
	
	/**
	 * 
	 * @param comment comment will print at the begin of each line in system.out
	 * @param arrayToPrint array with data to print
	 */
	private static void printIDs(String comment, String[] arrayToPrint) {
		for (int a = 0; a < arrayToPrint.length; a++) {
			allHWIDs += arrayToPrint[a] + ", ";
		}
	}
    public static String getHwId(){
        getHWIDs ();
        System.out.println(allHWIDs);
     return allHWIDs;
    }

    public static void main(String []args){
        getHWIDs ();
        System.out.println(allHWIDs);
    }
}
