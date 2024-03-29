import 'dart:async';

import 'package:eduwave/base/BaseModel.dart';
import 'package:eduwave/base/BaseStreamPresenter.dart';
import 'package:eduwave/common/userVote/model/user_vote_model_class_entity.dart';
import 'package:eduwave/common/userVote/repository/SubmitRepo.dart';
import 'package:eduwave/common/userVote/repository/UserVoteRepository.dart';
import 'package:eduwave/common/userVote/view/UserVoteView.dart';

class UserVotePresenter extends BaseStreamPresenter<UserVoteView> {
  StreamController<UserVoteModelData> streamController;
  UserVoteRepository repository;
  UserVoteView view;
  BaseModel baseModel;
  List<UserVoteModelClassDataVotesList> model;
  StreamController<UserVoteModelData> submitStreamController;
  SubmitRepo submitRepo;

  @override
  void onCreateState() {
    view.showLoadingDialog();
    streamController = StreamController.broadcast();
    repository = new UserVoteRepository();
    initializeStream(
        streamController, view.sendCallbackData, view.onError, view.onSuccess);
    repository.getUserVoteRepositoryData(streamController);
    submitStreamController = StreamController.broadcast();
    submitRepo = new SubmitRepo();
  }

  @override
  void onDispose() async{
    closeStream();
    view = null;
   await submitStreamController.close();
   await streamController.close();
    repository = null;
    submitRepo = null;


  }

  void submitData() {
    view.showLoadingDialog();
    initializeStream(submitStreamController, view.sendCallbackData,
        view.onError, view.onSuccess);
    submitRepo.postSubmitedData(submitStreamController, view.getSubmitBody());
  }

  @override
  void setView(UserVoteView view) {
    this.view = view;
  }

  @override
  closeStream() {

  }
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 