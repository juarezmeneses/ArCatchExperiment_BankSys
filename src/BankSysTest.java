import arcatch.ArCatch;
import arcatch.dsl.element.ExceptionElement;
import arcatch.dsl.element.ModuleElement;

public class BankSysTest {

	private static ModuleElement view;

	private static ModuleElement control;

	private static ModuleElement account;

	private static ModuleElement persistence;

	private static ExceptionElement accountExceptions;

	private static ExceptionElement persistenceExceptions;

	private static ExceptionElement controlExceptions;

	private static ExceptionElement allExceptions;

	public static void main(String[] args) {
		int version = 2;
		switch (version) {
		case 1:
			checkBankSys01("./bssrc/BankSys/src/", "./bsbin/banksys.jar");
			break;
		case 2:
			checkBankSys02("./bssrc/BankSys02/src/", "./bsbin/banksys02.jar");
			break;
		
		default:
			break;
		}
	}

	private static void checkBankSys01(String srcPath, String binPath) {
		ArCatch.config(srcPath, binPath);
		
		view = ArCatch.element().module("VIEW").matching("banksys.atm.[a-zA-Z_$][a-zA-Z0-9_$]*").build();

		control = ArCatch.element().module("CTL").matching("banksys.control.[a-zA-Z_$][a-zA-Z0-9_$]*").build();

		account = ArCatch.element().module("ACC").matching("banksys.account.[a-zA-Z_$][a-zA-Z0-9_$]*").build();

		persistence = ArCatch.element().module("PER").matching("banksys.persistence.[a-zA-Z_$][a-zA-Z0-9_$]*").build();

		accountExceptions = ArCatch.element().exception("ACCEx").matching("banksys.account.exception.[a-zA-Z_$][a-zA-Z0-9_$]*").build();

		persistenceExceptions = ArCatch.element().exception("PEREx").matching("banksys.persistence.exception.[a-zA-Z_$][a-zA-Z0-9_$]*").build();

		controlExceptions = ArCatch.element().exception("CTLEx").matching("banksys.control.exception.[a-zA-Z_$][a-zA-Z0-9_$]*").build();

		allExceptions = ArCatch.element().exception("AllEx").matching("banksys.[a-zA-Z]*.exception.[A-Za-z_$]+[a-zA-Z0-9_$]*Exception").build();

		check();
	}
	
	private static void checkBankSys02(String srcPath, String binPath) {
		ArCatch.config(srcPath, binPath);
		
		view = ArCatch.element().module("VIEW").matching("banksys.atm.[a-zA-Z_$][a-zA-Z0-9_$]*").build();

		control = ArCatch.element().module("CTL").matching("banksys.control.[a-zA-Z_$][a-zA-Z0-9_$]*").build();

		account = ArCatch.element().module("ACC").matching("banksys.account.[a-zA-Z_$][a-zA-Z0-9_$]*").build();

		persistence = ArCatch.element().module("PER").matching("banksys.persistence.[a-zA-Z_$][a-zA-Z0-9_$]*").build();

		accountExceptions = ArCatch.element().exception("ACCEx").matching("banksys.account.exception.[a-zA-Z_$][a-zA-Z0-9_$]*").build();

		persistenceExceptions = ArCatch.element().exception("PEREx").matching("banksys.persistence.exception.[a-zA-Z_$][a-zA-Z0-9_$]*").build();

		controlExceptions = ArCatch.element().exception("CTLEx").matching("banksys.control.exception.[a-zA-Z_$][a-zA-Z0-9_$]*").build();

		allExceptions = ArCatch.element().exception("AllEx").matching("banksys.[a-zA-Z]*.exception.[A-Za-z_$]+[a-zA-Z0-9_$]*Exception").build();

		check();
	}

	
	
	private static void check() {
		ArCatch.checker().addRule(ArCatch.rule().only(account).canRaise(accountExceptions).build());
		ArCatch.checker().addRule(ArCatch.rule().only(account).canSignal(accountExceptions).build());
		ArCatch.checker().addRule(ArCatch.rule().only(account).canSignal(accountExceptions).build());
		
		ArCatch.checker().addRule(ArCatch.rule().only(persistence).canRaise(persistenceExceptions).build());
		ArCatch.checker().addRule(ArCatch.rule().only(persistence).canSignal(persistenceExceptions).build());
		ArCatch.checker().addRule(ArCatch.rule().module(persistence).canSignalOnly(persistenceExceptions).build());
		
		ArCatch.checker().addRule(ArCatch.rule().module(control).canSignalOnly(controlExceptions).build());
		ArCatch.checker().addRule(ArCatch.rule().only(control).canSignal(controlExceptions).build());
		ArCatch.checker().addRule(ArCatch.rule().module(control).mustSignal(controlExceptions).build());
		ArCatch.checker().addRule(ArCatch.rule().module(control).canSignalOnly(controlExceptions).build());
		ArCatch.checker().addRule(ArCatch.rule().module(control).mustHandle(persistenceExceptions).build());
		ArCatch.checker().addRule(ArCatch.rule().only(control).canHandle(persistenceExceptions).build());

		ArCatch.checker().addRule(ArCatch.rule().module(view).canHandleOnly(controlExceptions).build());
		ArCatch.checker().addRule(ArCatch.rule().only(view).canHandle(controlExceptions).build());
		ArCatch.checker().addRule(ArCatch.rule().module(view).cannotRaise(allExceptions).build());
		ArCatch.checker().addRule(ArCatch.rule().module(view).cannotSignal(allExceptions).build());

		ArCatch.checker().checkAll();

	}
	
}