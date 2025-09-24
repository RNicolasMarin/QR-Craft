package com.qrcraft.core.presentation.components

sealed class BaseComponentAction {

    data object TopBarOnBackClicked: BaseComponentAction()

    data object TopBarOnRightClicked: BaseComponentAction()

    data object BottomNavigationBarOnCreate: BaseComponentAction()

    data object BottomNavigationBarOnHistory: BaseComponentAction()

}