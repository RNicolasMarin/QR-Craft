package com.qrcraft.core.presentation.components

sealed class BaseComponentAction {

    data object TopBarOnBackClicked: BaseComponentAction()

    data object TopBarOnRightClicked: BaseComponentAction()

    data object BottomNavigationBarOnScan: BaseComponentAction()

    data object BottomNavigationBarOnCreate: BaseComponentAction()

    data object BottomNavigationBarOnHistory: BaseComponentAction()

    data object SnackBarClearMessage: BaseComponentAction()

    data object DialogOnClosed: BaseComponentAction()

    data object DialogOnConfirm: BaseComponentAction()

    data object DialogOnErrorClosed: BaseComponentAction()

}