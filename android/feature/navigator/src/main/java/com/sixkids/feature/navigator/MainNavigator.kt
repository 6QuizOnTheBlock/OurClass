package com.sixkids.feature.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.sixkids.feature.signin.navigation.SignInRoute
import com.sixkids.feature.signin.navigation.navigateSignIn
import com.sixkids.feature.signin.navigation.navigateSignUp
import com.sixkids.feature.signin.navigation.navigateSignUpPhoto
import com.sixkids.student.board.navigation.StudentHomeRoute
import com.sixkids.student.board.navigation.navigateStudentHome
import com.sixkids.student.main.navigation.navigateJoinOrganization
import com.sixkids.student.main.navigation.navigateStudentOrganizationList
import com.sixkids.student.main.navigation.navigateStudentProfile
import com.sixkids.student.navigation.navigateStudentGroupCreate
import com.sixkids.student.navigation.navigateStudentGroupJoin
import com.sixkids.teacher.board.navigation.BoardRoute
import com.sixkids.teacher.board.navigation.navigateBoard
import com.sixkids.teacher.board.navigation.navigateChatting
import com.sixkids.teacher.board.navigation.navigatePost
import com.sixkids.teacher.board.navigation.navigatePostDetail
import com.sixkids.teacher.board.navigation.navigatePostWrite
import com.sixkids.teacher.challenge.navigation.navigateChallengeCreatedResult
import com.sixkids.teacher.challenge.navigation.navigateChallengeDetail
import com.sixkids.teacher.challenge.navigation.navigateChallengeHistory
import com.sixkids.teacher.challenge.navigation.navigateCreateChallenge
import com.sixkids.teacher.challenge.navigation.navigatePopupToHistory
import com.sixkids.teacher.home.navigation.HomeRoute
import com.sixkids.teacher.home.navigation.navigateHome
import com.sixkids.teacher.home.navigation.navigateRank
import com.sixkids.teacher.main.navigation.navigateNewOrganization
import com.sixkids.teacher.main.navigation.navigateProfile
import com.sixkids.teacher.main.navigation.navigateTeacherOrganizationList
import com.sixkids.teacher.manageclass.navigation.ManageClassRoute
import com.sixkids.teacher.manageclass.navigation.navigateManageClass
import com.sixkids.teacher.managestudent.navigation.ManageStudentRoute
import com.sixkids.teacher.managestudent.navigation.navigateManageStudent

class MainNavigator(
    val navController: NavHostController,
) {
    var bottomTabItems: State<List<MainNavigationTab>>? = null
    val startDestination = SignInRoute.defaultRoute
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination
    val currentTab: MainNavigationTab?
        @Composable get() = currentDestination
            ?.route
            ?.let { MainNavigationTab.find(it) }

    fun navigate(tab: MainNavigationTab) {
        val teacherNavOptions = navOptions {
            popUpTo(HomeRoute.defaultRoute) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        val studentNavOptions = navOptions {
            popUpTo(StudentHomeRoute.defaultRoute) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            // 선생님 바텀 네비게이션 탭
            MainNavigationTab.HOME -> navController.navigateHome(teacherNavOptions)
            MainNavigationTab.BOARD -> navController.navigateBoard(teacherNavOptions)
            MainNavigationTab.MANAGE_STUDENT -> navController.navigateManageStudent(teacherNavOptions)
            MainNavigationTab.MANAGE_CLASS -> navController.navigateManageClass(teacherNavOptions)
            // 학생 바텀 네비게이션 탭
            MainNavigationTab.STUDENT_HOME -> navController.navigateStudentHome(studentNavOptions)
            MainNavigationTab.STUDENT_BOARD -> {}
            MainNavigationTab.STUDENT_RELAY -> {}
            MainNavigationTab.STUDENT_CHALLENGE -> {}
        }
    }

    /**
     * Board Navigation
     */

    fun navigateBoard() {
        navController.navigate(BoardRoute.defaultRoute) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

    fun navigatePost() {
        navController.navigatePost()
    }

    fun navigatePostWrite() {
        navController.navigatePostWrite()
    }

    /**
     * Student Main Navigation
     */
    fun navigateStudentOrganizationList(){
        navController.navigateStudentOrganizationList()
    }

    fun navigateStudentProfile(){
        navController.navigateStudentProfile()
    }

    fun navigateJoinOrganization() {
        navController.navigateJoinOrganization()
    }

    fun navigatePostDetail(postId: Long) {
        navController.navigatePostDetail(postId)
    }


    /**
     * Home Navigation
     */
    fun navigateHome() {
        bottomTabItems = teacherTab() // 바텀 네비게이션 탭 초기화
        navController.navigate(HomeRoute.defaultRoute) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

    fun navigateRank() {
        navController.navigateRank()
    }

    /**
     * Manage Class Navigation
     */
    fun navigateManageClass(navOptions: NavOptions) {
        navController.navigate(ManageClassRoute.defaultRoute) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

    /**
     * Manage Student Navigation
     */
    fun navigateManageStudent() {
        navController.navigateManageStudent(navOptions {
            popUpTo(ManageStudentRoute.defaultRoute) {
                inclusive = true
            }
        })
    }

    /**
     * Student Home Navigation
     */
    fun navigateStudentHome() {
        bottomTabItems = studentTab() // 바텀 네비게이션 탭 초기화
        navController.navigateStudentHome(navOptions{
            popUpTo(StudentHomeRoute.defaultRoute){
                inclusive = true
            }
        })
    }

    /**
     * Student Group Navigation
     */
    //TODO : memberId 추가
    fun navigateStudentGroupCreate(memberId: Long) {
        navController.navigateStudentGroupCreate()
    }

    fun navigateStudentGroupJoin(memberId: Long) {
        navController.navigateStudentGroupJoin()
    }

    /**
     * SignIn Navigation
     */
    fun navigateSignIn() {
        navController.navigateSignIn()
    }

    fun navigateSignUp() {
        navController.navigateSignUp()
    }

    fun navigateSignUpPhoto(isTeacher: Boolean) {
        navController.navigateSignUpPhoto(isTeacher)
    }

    fun navigateChallengeHistory() {
        navController.navigateChallengeHistory()
    }

    fun navigateChallengeDetail(challengeId: Long, groupId: Long?) {
        navController.navigateChallengeDetail(challengeId, groupId)
    }

    fun navigatePopupToHistory() {
        navController.navigatePopupToHistory()
    }

    fun navigateCreateChallenge() {
        navController.navigateCreateChallenge()
    }

    fun navigateChallengeCreatedResult(challengeId: Long, title: String) {
        navController.navigateChallengeCreatedResult(challengeId, title)
    }

    fun navigateTeacherOrganizationList() {
        bottomTabItems = teacherTab() // 바텀 네비게이션 탭 초기화
        navController.navigateTeacherOrganizationList()
    }

    fun navigateNewOrganization() {
        navController.navigateNewOrganization()
    }

    fun navigateProfile() {
        navController.navigateProfile()
    }

    fun popBackStack() {
        navController.popBackStack()
    }

    fun navigateChatting() {
        navController.navigateChatting()
    }

    @Composable
    fun shouldShowBottomBar(): Boolean {
        val currentRoute = currentDestination?.route ?: return false
        return currentRoute in MainNavigationTab
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}

internal fun teacherTab(): State<List<MainNavigationTab>>{
    return mutableStateOf(
            listOf(
                MainNavigationTab.HOME,
                MainNavigationTab.BOARD,
                MainNavigationTab.MANAGE_STUDENT,
                MainNavigationTab.MANAGE_CLASS
            )
        )
}

internal fun studentTab(): State<List<MainNavigationTab>>{
    return mutableStateOf(
        listOf(
            MainNavigationTab.STUDENT_HOME,
            MainNavigationTab.STUDENT_BOARD,
            MainNavigationTab.STUDENT_RELAY,
            MainNavigationTab.STUDENT_CHALLENGE
        )
    )
}