package de.davis.passwordmanager.security.element;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import java.util.LinkedHashMap;
import java.util.Map;

import de.davis.passwordmanager.R;
import de.davis.passwordmanager.security.element.creditcard.CreditCardDetails;
import de.davis.passwordmanager.security.element.password.PasswordDetails;
import de.davis.passwordmanager.ui.elements.CreateSecureElementActivity;
import de.davis.passwordmanager.ui.elements.ViewSecureElementActivity;
import de.davis.passwordmanager.ui.elements.creditcard.CreateCreditCardActivity;
import de.davis.passwordmanager.ui.elements.creditcard.ViewCreditCardActivity;
import de.davis.passwordmanager.ui.elements.password.CreatePasswordActivity;
import de.davis.passwordmanager.ui.elements.password.ViewPasswordActivity;

public class SecureElementDetail {

    private final Class<? extends CreateSecureElementActivity> createClass;
    private final Class<? extends ViewSecureElementActivity> viewClass;
    private final Class<? extends ElementDetail> elementDetailClass;
    private final int title;
    private final int icon;

    public SecureElementDetail(Class<? extends CreateSecureElementActivity> createClass, Class<? extends ViewSecureElementActivity> viewClass, Class<? extends ElementDetail> elementDetailClass, @DrawableRes int icon, @StringRes int title) {
        this.createClass = createClass;
        this.viewClass = viewClass;
        this.elementDetailClass = elementDetailClass;
        this.icon = icon;
        this.title = title;
    }

    public int getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public Class<? extends CreateSecureElementActivity> getCreateActivityClass(){
        return createClass;
    }

    public Class<? extends ViewSecureElementActivity> getViewActivityClass() {
        return viewClass;
    }

    public Class<? extends ElementDetail> getElementDetailClass() {
        return elementDetailClass;
    }

    public static Map<Integer, SecureElementDetail> getRegisteredDetails(){
        LinkedHashMap<Integer, SecureElementDetail> map = new LinkedHashMap<>();

        map.put(SecureElement.TYPE_PASSWORD, new SecureElementDetail(CreatePasswordActivity.class, ViewPasswordActivity.class, PasswordDetails.class, R.drawable.ic_baseline_password_24, R.string.password));
        map.put(SecureElement.TYPE_CREDIT_CARD, new SecureElementDetail(CreateCreditCardActivity.class, ViewCreditCardActivity.class, CreditCardDetails.class, R.drawable.ic_baseline_credit_card_24, R.string.credit_card));

        return map;
    }

    public static SecureElementDetail getFor(SecureElement element) {
        return getFor(element.getType());
    }

    public static SecureElementDetail getFor(@SecureElement.ElementType int type) {
        return getRegisteredDetails().get(type);
    }
}

