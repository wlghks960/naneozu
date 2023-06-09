import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:get/get.dart';
import 'package:logger/logger.dart';
import 'package:nnz/src/components/register_form/share_popup.dart';
import 'package:nnz/src/services/user_provider.dart';

import '../config/config.dart';
import 'my_page_controller.dart';

class UserEditController extends GetxController {
  late final curPwdController;
  late final newPwdController;
  late final newPwdConfirmController;
  late final nickController;
  final storage = const FlutterSecureStorage();
  File? imageFile;
  final logger = Logger();
  RxBool nickChecked = false.obs;
  RxBool pwdChecked = false.obs;
  RxBool pwdConfirmChekced = false.obs;
  @override
  void onInit() {
    super.onInit();
    curPwdController = TextEditingController();
    newPwdController = TextEditingController();
    newPwdConfirmController = TextEditingController();
    nickController = TextEditingController();
  }

  bool test({required String value}) {
    return newPwdConfirmController.text == value ? true : false;
  }

  bool onPasswordValidate({required String text}) {
    String pattern =
        r"^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$";
    RegExp regExp = RegExp(pattern);
    if (!regExp.hasMatch(text)) {
      return false;
    }
    return true;
  }

  //닉네임 중복확인
  Future<void> nickValidate(
      {required String type, required String text}) async {
    logger.i("$type , $text");

    //이메일 유효성 검사 통과시 서버에서 api 통신 가능하면 주석 풀것

    // logger.i("이메일 통과됐나요? ${emailChecked.value}");

    try {
      final response =
          await UserProvider().getValidate(type: type, value: text);
      if (response.statusCode == 200) {
        logger.i(response.body);
        final available = response.body["available"];
        nickChecked(available);
        if (nickChecked.value == false) {
          showDialog(
              context: Get.context!,
              builder: (BuildContext context) {
                return const sharePopup(popupMessage: "중복된 닉네임입니다.");
              });
        } else {
          showDialog(
              context: Get.context!,
              builder: (BuildContext context) {
                return const sharePopup(popupMessage: "사용가능한 닉네임입니다.");
              });
        }
      } else {
        final errorMessage = "(${response.statusCode}): ${response.body}";
        logger.e(errorMessage);
        throw Exception(errorMessage);
      }
    } catch (e) {
      final errorMessage = "$e";
      logger.e(errorMessage);
      throw Exception(errorMessage);
    }
  }

  //유저 정보 수정
  Future<void> onUpdateUser() async {
    try {
      var userProvider = UserProvider();
      userProvider.httpClient.timeout = const Duration(seconds: 40);
      showDialog(
        context: Get.context!,
        barrierDismissible: false,
        builder: (BuildContext context) {
          return AlertDialog(
            content: SizedBox(
              height: 100, // 원하는 높이로 설정
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center, // 세로 방향 가운데 정렬
                children: [
                  Text(
                    "계정 수정중입니다.",
                    style: TextStyle(
                      color: Config.blackColor,
                      fontSize: 18,
                    ),
                  ),
                  const SizedBox(
                    height: 16,
                  ),
                  SpinKitCircle(
                    color: Config.yellowColor, // 애니메이션의 색상
                    size: 56, // 애니메이션의 크기
                  ),
                ],
              ),
            ),
          );
        },
      );
      final response = await userProvider.patchUser(
          image: imageFile!,
          oldPwd: curPwdController.text,
          newPwd: newPwdController.text,
          confirmNewPwd: newPwdConfirmController.text,
          nickname: nickController.text);
      Navigator.of(Get.context!).pop(); // Close the dialog

      if (response.statusCode == 204) {
        logger.i("되었어요");
        Get.find<MyPageController>().getMyInfo();
        ScaffoldMessenger.of(Get.context!).showSnackBar(
          const SnackBar(
            content: Text('계정 수정완료하였습니다.'),
          ),
        );

        Get.until((route) => route.isFirst);
        Get.toNamed("/myPage");
      } else if (response.statusCode == 401) {
        logger.e("${response.statusCode} ${response.statusText}");
      } else {
        logger.e("${response.statusCode} : ${response.statusText}");
        await showDialog(
            context: Get.context!,
            builder: (BuildContext context) {
              return AlertDialog(
                content: Text(response.body["message"]),
                actions: [
                  TextButton(
                    onPressed: () {
                      Navigator.of(context).pop();
                    },
                    child: const Text("확인"),
                  ),
                ],
              );
            });
      }
    } catch (e) {
      final errorMessage = "$e";
      logger.e(errorMessage);
      throw Exception(errorMessage);
    }
  }

  //유저 탈퇴
  Future<void> deleteUser() async {
    try {
      final response = await UserProvider().deleteUserService();
      logger.i(response.statusCode);
      if (response.statusCode == 204) {
        ScaffoldMessenger.of(Get.context!).showSnackBar(
          const SnackBar(content: Text('성공적으로 회원 탈퇴 되었습니다.')),
        );
        Get.offNamed('/app');
      } else {
        logger.e("${response.statusCode} ${response.statusText}");
      }
    } catch (e) {
      logger.e(e);
    }
  }
}
