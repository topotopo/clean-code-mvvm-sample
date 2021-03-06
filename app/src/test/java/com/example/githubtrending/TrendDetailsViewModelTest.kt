package com.example.githubtrending

import com.example.githubtrending.data.helper.ResultStatus
import com.example.githubtrending.domain.model.GitTrendingModel
import com.example.githubtrending.domain.usecase.GetTrendingDetailsUseCase
import com.example.githubtrending.helper.getTestValue
import com.example.githubtrending.presentation.details.TrendDetailsViewModel
import com.example.githubtrending.presentation.util.PageState
import com.example.githubtrending.presentation.util.PageStateHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import java.io.IOException

class TrendDetailsViewModelTest : BaseTest() {

    private lateinit var viewModel: TrendDetailsViewModel

    @Mock
    lateinit var gitTrendingModel: GitTrendingModel

    @Mock
    lateinit var useCase: GetTrendingDetailsUseCase

    @Before
    fun setUp() {
        viewModel =
            TrendDetailsViewModel(
                useCase,
                PageStateHelper()
            )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test fetch git trending details successfully should return a data and set page to success`() {
        val repositorySuccessResult = ResultStatus.Success(gitTrendingModel)
        coroutineTestRule.testDispatcher.runBlockingTest {
            Mockito.`when`(useCase.getTrendingDetails("")).thenReturn(repositorySuccessResult)
            viewModel.getTrendingDetails("")
        }
        Assert.assertTrue(viewModel.trendingModel.getTestValue() is GitTrendingModel)
        Assert.assertTrue(viewModel.pageState.getTestValue() is PageState.Success)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test fetch trending list failed should set page state to error`() {
        val repositoryErrorResult = ResultStatus.Error(IOException())
        coroutineTestRule.testDispatcher.runBlockingTest {
            Mockito.`when`(useCase.getTrendingDetails("")).thenReturn(repositoryErrorResult)
            viewModel.getTrendingDetails("")
        }
        Assert.assertTrue(viewModel.pageState.getTestValue() is PageState.Error)
    }
}